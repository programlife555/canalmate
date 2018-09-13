package com.ppdai.canalmate.api.controller.canal.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ppdai.canalmate.api.core.BaseController;
import com.ppdai.canalmate.api.core.PageResult;
import com.ppdai.canalmate.api.core.Result;
import com.ppdai.canalmate.api.model.canal.server.CanalClientStatus;
import com.ppdai.canalmate.api.model.canal.server.CanalServerConfig;
import com.ppdai.canalmate.api.model.canal.server.CanalServerConfigShow;
import com.ppdai.canalmate.api.model.canal.server.CanalServerStatus;
import com.ppdai.canalmate.api.model.canal.server.ClientConfig;
import com.ppdai.canalmate.api.model.canal.server.DestinationsConfig;
import com.ppdai.canalmate.api.service.canal.JdbcTemplateService;
import com.ppdai.canalmate.api.service.canal.ProcessMonitorService;
import com.ppdai.canalmate.common.cons.CanalConstants;
import com.ppdai.canalmate.common.model.ZKDestinationBean;
import com.ppdai.canalmate.common.model.ZKDestinationClientRunningNode;
import com.ppdai.canalmate.common.model.ZKDestinationClusterNode;
import com.ppdai.canalmate.common.utils.CanalPropertyUtils;
import com.ppdai.canalmate.common.utils.CanalZKUtils;
import com.ppdai.canalmate.common.utils.P;
import com.ppdai.canalmate.common.utils.ReponseEnum;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value = "CanalProcessController", description = "进程管理的接口类")
@RestController
@EnableAutoConfiguration
@RequestMapping("/processMonitor") 
public class CanalProcessController extends BaseController{

	Logger logger = LoggerFactory.getLogger(CanalProcessController.class);
	
    @Qualifier(value = "processMonitorService")
    @Autowired
    private ProcessMonitorService processMonitorService;
    

    @ApiOperation(value = "列出canal server config的列表",httpMethod ="GET", response = Result.class)
    @RequestMapping(value = "/canalServerConfig/list", method = RequestMethod.GET)
    public Result listCanalServerConfig(@RequestParam(value = "canal_server_name", required=false) String canalServerName,
		   @RequestParam(value = "canal_server_host", required=false) String canalServerHost,
		   @RequestParam(value = "pageNum", required=false) String pageNum,
		   @RequestParam(value = "numberPerPage", required=false) String numberPerPage) {
    	
    	List<CanalServerConfigShow> canalServerConfigShowList = new ArrayList<CanalServerConfigShow>();
    	Map<String,String> argsMap=new HashMap<String,String>();
    	if (StringUtils.isNotBlank(pageNum)) {
        	argsMap.put("pageNum", pageNum);
        }else {
        	argsMap.put("pageNum", "1");
        }
    	if (StringUtils.isNotBlank(numberPerPage)) {
        	argsMap.put("numberPerPage", numberPerPage);
        }else {
        	argsMap.put("numberPerPage", "10");
        }
    	if (StringUtils.isNotBlank(canalServerName)) {
        	argsMap.put("canal_server_name", canalServerName);
        }
    	if (StringUtils.isNotBlank(canalServerHost)) {
        	argsMap.put("canal_server_host", canalServerHost);
        }
    	
    	Map map = processMonitorService.listCanalServerConfig(argsMap);
    	canalServerConfigShowList=(List<CanalServerConfigShow>)map.get("resultList");
    	
    	//重新封装从数据库中的这个list，根据zk的状态，填入状态信息
    	canalServerConfigShowList=processMonitorService.getCanalServerStatusBeanList(canalServerConfigShowList);
    	
    	Integer cnt=(Integer)map.get("cnt");
    	
    	PageResult result = new PageResult();
        result.setCode(ReponseEnum.SUCCEED.getResCode());
        result.setMessage(ReponseEnum.SUCCEED.getResMsg());
        result.setData(canalServerConfigShowList);
        result.setTotalNum(String.valueOf(cnt));
        return result;
    }
    
    @ApiOperation(value = "列出canal server 状态列表",httpMethod ="GET", response = Result.class)
    @RequestMapping(value = "/canalServerstatus/list", method = RequestMethod.GET)
    public Result listCanalServerStatus(@ApiParam(required = true, value = "canal_server_config的主键") @RequestParam(value = "id", required=true) String id) {
    	
    	List<CanalServerConfig> canalServerConfigList = new ArrayList<CanalServerConfig>();
    	//根据server id ，从数据库取对应的server配置
    	CanalServerConfig canalServerConfig=processMonitorService.selectCanalServerConfigByPrimaryKey(Long.valueOf(id));
    	String zkAddress=CanalPropertyUtils.getPropertyValueByKey(canalServerConfig.getCanalServerConfiguration(),"canal.zkServers");
    	String canalServerName=canalServerConfig.getCanalServerName();
    	String canalServerHost=canalServerConfig.getCanalServerHost();
    	String canalServerPort=canalServerConfig.getCanalServerPort();
    	String standbyServerHost=canalServerConfig.getStandbyServerHost();
    	String standbyServerPort=canalServerConfig.getStandbyServerPort();
    	String canalServerHostPort=canalServerHost.trim()+":"+canalServerPort.trim();
    	String standbyServerHostPort=standbyServerHost.trim()+":"+standbyServerPort.trim();

    	Map<String,CanalServerStatus> map=new HashMap<String,CanalServerStatus>();

       	//先从zk里面取该server下所有destination的状态信息，给后面的判断准备数据
    	List<ZKDestinationBean> zKDestinationBeanList=CanalZKUtils.getZKDestinationBeanListFromZKAddress(zkAddress);
    	/* 
    	 * 判断canal server的状态，分2步
    	 * 第一步：先循环zk取该server所有destination的数据，从destination判断所有的server，不从zk的/otter/canal/cluster 路径判断。
    	 * 把从zk取的数据做比较基准，数据都是zk存在的。
    	 * 若是active=true，即：zk存在，db存在。标绿,说明是正常运行，正常配置的active节点
    	 * 若是active=true，即：zk存在，但db不存在，标黄,说明是正常运行，但DB里面错误配置的节点
    	 * 若是active=false，即：zk存在，db存在，标灰，说明是standby运行，正常配置的standby节点
    	 * 若是active=false，即：zk存在，但db不存在，标黄,说明是standby运行，但DB里面错误配置的节点
    	 * 
    	 * 第二步：根据db的数据做比较基准
    	 * 标红的情况：server在db有配置，但zk不存在
    	 * 
    	 * */
    	//第一步：先循环zk取的数据，在zk存在的情况下，判断是否与db里面匹配
    	//循环每个destination
    	for(ZKDestinationBean zKDestinationBean:zKDestinationBeanList) {
    		if(zKDestinationBean.getIsStatusOK()==false) {
    			break;
    		}
    		
    		String destinationName=zKDestinationBean.getDestinationName();
    		if(zKDestinationBean.getzKDestinationRunningNode()==null) {
    			logger.debug("destinationName："+destinationName+"没有server运行它，不计算，直接跳过");
    			continue;
    		}
    		
    		//map ，取出最开始的那个list，放到map,先不定义standby active
    		//根据 ls /otter/canal/cluster，找到注册在集群上的canal server
    		List<String> ipAndPortList=CanalZKUtils.getZKCanalServerListFromZKAddress(zkAddress);
          	Set<String>  canalServerIpSet=new HashSet<String>();
          	for(String addressPort:ipAndPortList) {
				CanalServerStatus status=new CanalServerStatus();
          		status.setAddressPort(addressPort);
				status.setCanalServerName(canalServerName);
				status.setColor(CanalConstants.GRAY);
				status.setComment("该server:"+addressPort+"在zk中/otter/canal/cluster已注册，默认标志为GRAY");
				map.put(addressPort,status);

          	}
    		
    		
    		//从每个destination中取 cluster 节点的状态
    		List<ZKDestinationClusterNode> list=zKDestinationBean.getzKDestinationClusterNodeList();
    		for(ZKDestinationClusterNode node: list) {
    			String addressPort=node.getAddressPort();//例如： xxxxx:11111，从zk获取
    			//若/otter/canal/destinations/xxxxx/running 的节点与/otter/canal/destinations/xxxx/cluster 下相同，则该节点为active
    			Boolean active=node.getActive();
    			if(active) {//若是active
    				if(addressPort.equals(canalServerHostPort)||addressPort.equals(standbyServerHostPort)) {//若跟db的配置表中的相同
    					CanalServerStatus status=new CanalServerStatus();
    					status.setAddressPort(addressPort);
    					status.setCanalServerName(canalServerName);
    					status.setColor(CanalConstants.GREEN);
    					status.setComment("该server:"+addressPort+"在zk中是active状态，且与数据库中的host字段一致");
    					map.put(addressPort,status);
    				}else {//zk与db配置的不匹配
    					CanalServerStatus status=new CanalServerStatus();
    					status.setAddressPort(addressPort);
    					status.setCanalServerName(canalServerName);
    					status.setColor(CanalConstants.YELLOW);
    					status.setComment("该server:"+addressPort+"在zk中是active状态，但数据库中没有配置该server的host信息");
    					map.put(addressPort,status);
    				}
    			}else {//若是standby
    				if(addressPort.equals(canalServerHostPort)||addressPort.equals(standbyServerHostPort)) {//若跟db的配置表中的相同
    					CanalServerStatus status=new CanalServerStatus();
    					status.setAddressPort(addressPort);
    					status.setCanalServerName(canalServerName);
    					status.setColor(CanalConstants.GRAY);
    					status.setComment("该server:"+addressPort+"在zk中是standby状态，且与数据库中的host字段一致");
    					map.put(addressPort,status);
    				}else {//zk与db配置的不匹配
    					CanalServerStatus status=new CanalServerStatus();
    					status.setAddressPort(addressPort);
    					status.setCanalServerName(canalServerName);
    					status.setColor(CanalConstants.YELLOW);
    					status.setComment("该server:"+addressPort+"在zk中是standby状态，但数据库中没有配置该server的host信息");
    					map.put(addressPort,status);
    				}
    			}
    		}
    		
    	}
    	
    	//第二步：根据db的数据做比较基准,若server在db有配置，但zk不存在，说明对应的server挂了
    	if(!map.containsKey(canalServerHostPort)) {
    		CanalServerStatus status=new CanalServerStatus();
			status.setAddressPort(canalServerHostPort);
			status.setCanalServerName(canalServerName);
			status.setColor(CanalConstants.RED);
			status.setComment("该server:"+canalServerHostPort+"在zk中不存在，但数据库中有该host的记录。有可能该host挂掉，或者该host信息配置错误");

			map.put(canalServerHostPort,status);
    	}
    	if(!map.containsKey(standbyServerHostPort)) {
    		CanalServerStatus status=new CanalServerStatus();
			status.setAddressPort(standbyServerHostPort);
			status.setCanalServerName(canalServerName);
			status.setColor(CanalConstants.RED);
			status.setComment("该server:"+canalServerHostPort+"在zk中不存在，但数据库中有该host的记录。有可能该host挂掉，或者该host standby信息配置错误");
			map.put(standbyServerHostPort,status);
    	}
    	//加到list中
    	List<CanalServerStatus> canalServerStatusList=new ArrayList<CanalServerStatus>();
    	for(Map.Entry<String,CanalServerStatus> entry : map.entrySet()) {
    		CanalServerStatus canalServerStatus=entry.getValue();
    		canalServerStatusList.add(canalServerStatus);
    	}
    	
    	
    	//判断canal client的状态
    	List<CanalClientStatus> canalClientStatusList=processMonitorService.getCanalClientStatusBeanList(id,zKDestinationBeanList);

    	Map<String, Object> data = new HashMap<String, Object>();
    	
    	data.put("canalServerStatusList", canalServerStatusList);
    	data.put("canalClientStatusList", canalClientStatusList);
    	
	   	Result result = new Result();
	    result.setCode(ReponseEnum.SUCCEED.getResCode());
	    result.setMessage(ReponseEnum.SUCCEED.getResMsg());
	    result.setData(data);

        return result;
    }
    

}


