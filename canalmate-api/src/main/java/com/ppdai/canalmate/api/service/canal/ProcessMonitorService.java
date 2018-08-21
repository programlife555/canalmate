package com.ppdai.canalmate.api.service.canal;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Component;

import com.ppdai.canalmate.api.controller.canal.server.CanalProcessController;
import com.ppdai.canalmate.api.dao.canal.server.CanalServerConfigMapper;
import com.ppdai.canalmate.api.dao.canal.server.DestinationsConfigMapper;
import com.ppdai.canalmate.api.model.canal.server.CanalClientStatus;
import com.ppdai.canalmate.api.model.canal.server.CanalServerConfig;
import com.ppdai.canalmate.api.model.canal.server.CanalServerConfigShow;
import com.ppdai.canalmate.api.model.canal.server.ClientConfig;
import com.ppdai.canalmate.api.model.canal.server.DestinationsConfig;
import com.ppdai.canalmate.api.model.canal.server.TbCanalinstanceConfig;
import com.ppdai.canalmate.api.service.canal.server.CanalServerConfigService;
import com.ppdai.canalmate.common.cons.CanalConstants;
import com.ppdai.canalmate.common.model.ZKDestinationBean;
import com.ppdai.canalmate.common.model.ZKDestinationClientRunningNode;
import com.ppdai.canalmate.common.utils.CanalPropertyUtils;
import com.ppdai.canalmate.common.utils.CanalZKUtils;
import com.ppdai.canalmate.common.utils.DateUtil;
import com.ppdai.canalmate.common.utils.P;



@Component
public class ProcessMonitorService {
	
	Logger logger = LoggerFactory.getLogger(ProcessMonitorService.class);

    @Autowired
    @Lazy
    @Qualifier("mysqlJdbcTemplate")
    private JdbcTemplate mysqlJdbcTemplate;//通过jdbcTemplate的方式操作数据库,可以和下面的mybatis的方式共存
    
    @Qualifier(value = "jdbcTemplateService")
    @Autowired
    private JdbcTemplateService jdbcTemplateService;
  
    
    @Autowired
    private CanalServerConfigMapper canalServerConfigMapper;//通过myBatis的方式操作数据库,可以和上面的jdbcTemplate的方式共存

    
    public List<DestinationsConfig> selectDestinationConfigByArgs(Map<String,String> argsMap) {
    	
    	
      	String destinationName=argsMap.get("destination_name");
      	String canalId=argsMap.get("canal_id");
      //拼接sql
    	String temp="select id,canal_id,destination_name,description,destination_configuration,standby_configuration,"
    			+ " inserttime,updatetime,isactive\r\n" + 
    			"from destinations_config where 1=1 and isactive=1";
        StringBuffer selectByParamSql = new StringBuffer(temp);
        
        if (StringUtils.isNotBlank(destinationName)) {
        	selectByParamSql.append(" and destination_name = '").append(destinationName).append("' ");
        }
        if (StringUtils.isNotBlank(canalId)) {
        	selectByParamSql.append(" and canal_id = ").append(canalId).append(" ");
        }
        
        List<DestinationsConfig> resultList = new ArrayList<DestinationsConfig>();
        mysqlJdbcTemplate.query(selectByParamSql.toString(), new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet rs) throws SQLException {
            	DestinationsConfig dto=new DestinationsConfig();
            	dto.setId(rs.getLong("id"));
            	dto.setCanalId(rs.getLong("canal_id"));
            	dto.setDestinationName(rs.getString("destination_name"));
            	dto.setDescription(rs.getString("description"));
            	dto.setDestinationConfiguration(rs.getString("destination_configuration"));
            	dto.setStandbyConfiguration(rs.getString("standby_configuration"));
            	dto.setInserttime(rs.getTimestamp("inserttime"));
            	dto.setUpdatetime(rs.getTimestamp("updatetime"));
            	
                resultList.add(dto);
            }
        });
        
        return resultList;
        
    }
    
    public Map listCanalServerConfig(Map<String,String> argsMap) {
    	String canalServerName=argsMap.get("canal_server_name");
    	String canalServerHost=argsMap.get("canal_server_host");
    	Integer pageNum=Integer.parseInt(argsMap.get("pageNum"));
    	Integer numberPerPage=Integer.parseInt(argsMap.get("numberPerPage"));
    	Integer start=(pageNum-1)*numberPerPage;//mysql的索引从0开始
    	Integer offset=numberPerPage;//mysql的索引从0开始
		Map result=new HashMap();

    	//拼接sql
    	String temp="select id,canal_server_type,canal_home,canal_server_name,canal_server_host,canal_server_port,canal_server_configuration,"
    			+ " standby_server_host,standby_server_port,standby_server_configuration,inserttime,updatetime \r\n" + 
    			"from canal_server_config where 1=1 and isactive=1";
        StringBuffer selectByParamSql = new StringBuffer(temp);
        
        if (StringUtils.isNotBlank(canalServerName)) {
        	selectByParamSql.append(" and canal_server_name like '%").append(canalServerName).append("%' ");
        }
        if (StringUtils.isNotBlank(canalServerHost)) {
        	selectByParamSql.append(" and canal_server_host like '%").append(canalServerHost).append("%' ");
        }
        
        //查询总数
    	String cntSql="select count(*) from ("+selectByParamSql.toString()+")cnt";
    	Integer cnt = mysqlJdbcTemplate.queryForObject(cntSql, Integer.class, new Object[]{});
        //查询当前页数据
    	selectByParamSql.append(" limit "+start+","+offset);
//    	P.p("selectByParamSql==========:"+selectByParamSql.toString());
        List<CanalServerConfigShow> resultList = new ArrayList<CanalServerConfigShow>();
        mysqlJdbcTemplate.query(selectByParamSql.toString(), new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet rs) throws SQLException {
            	CanalServerConfigShow dto=new CanalServerConfigShow();
            	dto.setId(rs.getLong("id"));
            	dto.setCanalServerType(rs.getString("canal_server_type"));
            	dto.setCanalHome(rs.getString("canal_home"));
            	dto.setCanalServerName(rs.getString("canal_server_name"));
            	dto.setCanalServerHost(rs.getString("canal_server_host"));
            	dto.setCanalServerPort(rs.getString("canal_server_port"));
            	dto.setCanalServerConfiguration(rs.getString("canal_server_configuration"));
            	
            	dto.setStandbyServerHost(rs.getString("standby_server_host"));
            	dto.setStandbyServerPort(rs.getString("standby_server_port"));
            	dto.setStandbyServerConfiguration(rs.getString("standby_server_configuration"));
            	
            	dto.setInserttimeStr(DateUtil.TimeStampToStr(rs.getTimestamp("inserttime")));
            	dto.setUpdatetimeStr(DateUtil.TimeStampToStr(rs.getTimestamp("updatetime")));
            	
                resultList.add(dto);
            }
        });
        
        result.put("resultList", resultList);
        result.put("cnt", cnt);
        return result;
    }
    
    /*
     * 查询canalServer的ID，找到canaServerConfig的实例
     * */
    public CanalServerConfig selectCanalServerConfigByPrimaryKey(Long id) {
    	CanalServerConfig canalServerConfig=canalServerConfigMapper.selectByPrimaryKey(id);
    	return canalServerConfig;
    }
    
    public DestinationsConfig selectDestinationsConfigByArgs(Map<String,String> argsMap) {
    	String temp="select id, canal_id, destination_name, description, destination_configuration, "
    			+ "standby_configuration, inserttime, updatetime, isactive \r\n" + 
    			"from destinations_config where 1=1 and isactive=1 ";
        StringBuffer selectByParamSql = new StringBuffer(temp);
        String canalId=argsMap.get("canal_id");
        if (StringUtils.isNotBlank(canalId)) {
        	selectByParamSql.append(" and canal_id = ").append(Integer.parseInt(canalId)).append(" ");
        }
        String destinationName=argsMap.get("destination_name");
        if (StringUtils.isNotBlank(destinationName)) {
        	selectByParamSql.append(" and destination_name = '").append(destinationName).append("' ");
        }
//        P.p(selectByParamSql.toString());
        DestinationsConfig destinationConfig=null;
        try {
        	destinationConfig=(DestinationsConfig)mysqlJdbcTemplate.queryForObject(selectByParamSql.toString(), 
        			new BeanPropertyRowMapper(DestinationsConfig.class));
        }catch (Exception e) {
        	System.out.println("该查询结果若为0，会报错。若实际为0，可忽略");
//        	e.printStackTrace();
        }
        
        return destinationConfig;
    }
    

  /*
	 * 根据传入的一个canal server的id(canal_id)和该server在zk上所有destination列表，结合数据库配置信息，返回所有的canal client 信息。
	 * */
  public  List<CanalClientStatus> getCanalClientStatusBeanList(String canalId,List<ZKDestinationBean> zKDestinationBeanList) {
  	List<CanalClientStatus> canalClientStatusList=new ArrayList<CanalClientStatus>();
  	Set<String> zkDestinationSet=new HashSet<String>();
  	/* 
  	 * 判断canal client的状态，分2步
  	 * 第一步：先循环zk取的数据
  	 * 把从zk取的数据做比较基准，数据都是zk存在的。
  	 * 循环每个destination.
  	 * 若该destination有client信息，且db中有该destination信息，说明与数据库信息一致，且有client在消费该destination，标绿
  	 * 若该destination有client信息，但db中没有该destination信息，说明zk与数据库信息不一致，有可能数据库没配置该destination。标黄
  	 * 若该destination没有client信息，说明没有client在消费该destination，有可能该client挂了。标红
  	 * 
  	 * 第二步：根据db的数据做比较基准，主要根据destinationName，因为这个字段唯一索引，与zk的destinationName一一对应。
  	 * 若db中有该canalserver的destination的信息，但zk中没有，说明db中该destination有可能多余，需要删除。
  	 * 
  	 * */
  	//第一步：先循环zk取的数据，在zk存在的情况下，判断是否与db里面匹配
  	//循环每个destination
  	for(ZKDestinationBean zKDestinationBean:zKDestinationBeanList) {
  		//先从每一个destination取对应的数据库信息
  		String destinationName=zKDestinationBean.getDestinationName();
  		if(zKDestinationBean.getzKDestinationRunningNode()==null) {
  			logger.debug("destinationName："+destinationName+"没有server运行它,有可能该destination已经摘除，zk信息过时，不计算，直接跳过");
  			continue;
  		}
  		
  		CanalClientStatus clientStatus=new CanalClientStatus();
  		zkDestinationSet.add(destinationName);//放到map中，给后面的db判断准备数据
  		clientStatus.setDestinationName(destinationName);
  		
  		//查询条件，该canalserver下的destination名为destinationName的记录
  		Map<String,String> argsMap=new HashMap<String,String>();
  		argsMap.put("destination_name", destinationName.trim());
  		argsMap.put("canal_id", canalId);
  		//查询destination
//  		if(processMonitorService==null) {
//  			logger.error("=========processMonitorService is null");
//  		}
  		DestinationsConfig destinationsConfig=selectDestinationsConfigByArgs(argsMap);
  		//根据destination id，查询client config信息，一个destination唯一对应一个client
  		

  		//从每个destination中取 cluster 节点的状态
  		ZKDestinationClientRunningNode clientRunningNode=zKDestinationBean.getzKDestinationClientRunningNode();
  		if(clientRunningNode!=null){//说明该destination有客户端消费
  			//if(destinationsConfig!=null&&destinationList.size()>0) {//若数据库中有该destination的信息
  			if(destinationsConfig!=null) {//若数据库中有该destination的信息
  				StringBuilder comment=new StringBuilder();
  				//取clientName
  				argsMap.clear();
  	    		argsMap.put("destination_id", String.valueOf(destinationsConfig.getId()));
  	    		ClientConfig clientConfig=jdbcTemplateService.selectClientConfigByArgs(argsMap);
  	    		String clientName="";
  	    		if(clientConfig!=null) {
  	    			clientName=clientConfig.getClientName();
  	    			comment.append("destination:"+destinationName+"有client消费");
  	    		}else {
  	    			comment.append("destination:"+destinationName+"在数据库没有配置client或配置了多个client");
  	    		}
  	    		//取配置文件，从配置文件取对应的mysql的地址
  	            String destinationConfiguration = destinationsConfig.getDestinationConfiguration();
  	    		String mysqlAddress=CanalPropertyUtils.getPropertyValueByKey(destinationConfiguration, "canal.instance.master.address");
  				String clientAddressPort=clientRunningNode.getAddress();
  				clientStatus.setClientName(clientName);
  				clientStatus.setMysqlAddress(mysqlAddress);
  				clientStatus.setClientAddressPort(clientAddressPort);
  				clientStatus.setColor(CanalConstants.GREEN);
  				clientStatus.setCanalClientStatusCode(CanalConstants.RUNNING);
  				clientStatus.setComment(comment.toString());
  			}else {//若数据库中没有该destination的信息
  				StringBuilder comment=new StringBuilder();
  				comment.append("destination:"+destinationName+"有client消费,但db中没有该destination信息，说明zk与数据库信息不一致，可能数据库没配置该destination");
  				String clientAddressPort=clientRunningNode.getAddress();
  				clientStatus.setClientAddressPort(clientAddressPort);
  				clientStatus.setColor(CanalConstants.YELLOW);
  				clientStatus.setCanalClientStatusCode(CanalConstants.RUNNING);
  				clientStatus.setComment(comment.toString());
  			}
  		}else {//若该destination没有client消费
  			if(destinationsConfig!=null) {//若数据库中有该destination的信息
  				StringBuilder comment=new StringBuilder();
  				//取clientName
  				argsMap.clear();
  	    		argsMap.put("destination_id", String.valueOf(destinationsConfig.getId()));
  	    		ClientConfig clientConfig=jdbcTemplateService.selectClientConfigByArgs(argsMap);
  	    		String clientName="";
  	    		if(clientConfig!=null) {
  	    			clientName=clientConfig.getClientName();
  	    			comment.append("destination为"+destinationName+"没有client消费");
  	    		}else {
  	    			comment.append("destination:"+destinationName+"没有client消费,数据库没有配置client或配置了多个client");
  	    		}
  	    		//取配置文件，从配置文件取对应的mysql的地址
	    			String destinationConfiguration = destinationsConfig.getDestinationConfiguration();
		    		String mysqlAddress=CanalPropertyUtils.getPropertyValueByKey(destinationConfiguration, "canal.instance.master.address");
  				clientStatus.setClientName(clientName);
		    		clientStatus.setMysqlAddress(mysqlAddress);
	    			clientStatus.setClientAddressPort(null);
	    			clientStatus.setColor(CanalConstants.RED);
	    			clientStatus.setCanalClientStatusCode(CanalConstants.NOT_RUNNING);
	    			clientStatus.setComment(comment.toString());
  			}else {//若数据库中没有该destination的信息
  				StringBuilder comment=new StringBuilder();
  				comment.append("destination:"+destinationName+"没有client消费,且db中没有该destination信息，可能数据库配置该destination有误");
  				clientStatus.setColor(CanalConstants.YELLOW);
  				clientStatus.setCanalClientStatusCode(null);
  				clientStatus.setComment(comment.toString());
  			}
  		}
  		//把该destination对应的client放到列表
  		canalClientStatusList.add(clientStatus);
  		
  	}
  	
  	//第二步：根据db的数据做比较基准，主要根据destinationName，因为这个字段唯一索引，与zk的destinationName一一对应(已放入zkDestinationSet)。
		//查询条件，该canalserver下的所有destination
		Map<String,String> argsMap=new HashMap<String,String>();
		argsMap.put("canal_id", canalId);
		List<DestinationsConfig> destinationList=selectDestinationConfigByArgs(argsMap);
		for(DestinationsConfig destinationsConfig : destinationList) {
			String destinationName=destinationsConfig.getDestinationName();
			if(!zkDestinationSet.contains(destinationName)) {
	    		CanalClientStatus clientStatus=new CanalClientStatus();
	    		clientStatus.setClientAddressPort(null);
  			clientStatus.setColor(CanalConstants.YELLOW);
  			clientStatus.setComment("destination为"+destinationName+"在zk中不存在或获取zk数据错误");
			
      		canalClientStatusList.add(clientStatus);
			}
			
		}
		
		return canalClientStatusList;
  }
  
  
	
	/*
	 * 重新封装从数据库中的canalServer list，根据zk的状态，填入bean的状态信息，并返回list
	 * */
  public  List<CanalServerConfigShow> getCanalServerStatusBeanList(List<CanalServerConfigShow> canalServerConfigShowList) {
  	
  	//循环当前页的canal serverlist，从zk取对应的状态
  	for(CanalServerConfigShow canalServerConfig: canalServerConfigShowList) {
  		//找到该server集群下有几台canal server运行
      	String zkAddress=CanalPropertyUtils.getPropertyValueByKey(canalServerConfig.getCanalServerConfiguration(),"canal.zkServers");
//      	P.pr("zkAddress:"+zkAddress);
      	List<String> ipAndPortList=CanalZKUtils.getZKCanalServerListFromZKAddress(zkAddress);
      	Set<String>  canalServerIpSet=new HashSet<String>();
      	for(String ipAndPort:ipAndPortList) {
      		String ip=ipAndPort.split(":")[0];
//      		P.pr(" canalServerIpSet ,zk canal server ip:"+ip);
      		canalServerIpSet.add(ip);
      	}
      	//得到该master的canal.ip，先从配置文件取
      	String masterCanalIp=CanalPropertyUtils.getPropertyValueByKey(canalServerConfig.getCanalServerConfiguration(),"canal.ip");
      	String standbyCanalIp=CanalPropertyUtils.getPropertyValueByKey(canalServerConfig.getStandbyServerConfiguration(),"canal.ip");
      	//若配置文件中没有（说明配置文件出错了），从数据库字段中取
      	if(StringUtils.isBlank(masterCanalIp)) {
      		masterCanalIp=canalServerConfig.getCanalServerHost();
      	}
      	if(StringUtils.isBlank(standbyCanalIp)) {
      		standbyCanalIp=canalServerConfig.getStandbyServerHost();
      	}
      	
//        P.pr("master IP："+master_canal_ip+",standby_canal_ip:"+standby_canal_ip);
      	//判断master是否运行，主要是设置StatusCode
      	if(canalServerIpSet.contains(masterCanalIp)) {
      		logger.debug("master RUNNING");
      		canalServerConfig.setServerStatusCode(CanalConstants.RUNNING);
      	}else {
      		logger.debug("master NOT RUNNING");
      		canalServerConfig.setServerStatusCode(CanalConstants.NOT_RUNNING);
      	}
      	//判断standby是否运行
      	if(canalServerIpSet.contains(standbyCanalIp)) {
      		logger.debug("standby RUNNING");
      		canalServerConfig.setStandbyStatusCode(CanalConstants.RUNNING);
      	}else {
      		logger.debug("standby NOT RUNNING");
      		canalServerConfig.setStandbyStatusCode(CanalConstants.NOT_RUNNING);
      	}
      	
      	String canalServerPort=canalServerConfig.getCanalServerPort();
      	String standbyServerPort=canalServerConfig.getStandbyServerPort();

      	canalServerConfig.setCanalServerHostPort(masterCanalIp+":"+canalServerPort);
      	canalServerConfig.setStandbyServerHostPort(standbyCanalIp+":"+standbyServerPort);
  	}
  	
  	return canalServerConfigShowList;
  	
  }
	
    
    
    
//    /*
//     * 查询canalServer的配置文件，根据属性名，得到属性值。会先查数据库，然后正则提取。
//     * */
//    public String getCanalServerPropertyByKey(Long id,String key) {
//
//    	CanalServerConfig canalServerConfig=selectCanalServerConfigByPrimaryKey(id);
//    	String configProperty=canalServerConfig.getCanalServerConfiguration();
////    	P.p(configProperty);
////    	P.p("key:"+key);
//    	Pattern p = Pattern.compile("(.*?)"+key+"(.*?)=(.*?)\r");
//        Matcher m = p.matcher(configProperty);
//        StringBuilder strs = new StringBuilder();
//        while (m.find()) {
//        	String line=m.group(3).trim();
//        	strs.append(line);
//        }
//    	
//    	return strs.toString();
//    }
    
}
