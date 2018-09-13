package com.ppdai.canalmate.api.service.canal;


import com.alibaba.otter.canal.protocol.position.LogPosition;
import com.ppdai.canalmate.api.controller.canal.server.CanalGapController;
import com.ppdai.canalmate.api.core.Result;
import com.ppdai.canalmate.api.core.ResultCode;
import com.ppdai.canalmate.api.dao.canal.server.TbCanalinstanceConfigMapper;
import com.ppdai.canalmate.api.entity.dto.MenuDto;
import com.ppdai.canalmate.api.entity.dto.ResultResponse;
import com.ppdai.canalmate.api.entity.dto.UserDto;
import com.ppdai.canalmate.api.entity.dto.canal.CanalGapDto;
import com.ppdai.canalmate.api.entity.dto.canal.CanalInstanceConfigDto;
import com.ppdai.canalmate.api.model.canal.server.CanalServerConfig;
import com.ppdai.canalmate.api.model.canal.server.DestinationsConfig;
import com.ppdai.canalmate.api.model.canal.server.TbCanalinstanceConfig;
import com.ppdai.canalmate.common.cons.CanalConstants;
import com.ppdai.canalmate.common.model.ZKDestinationBean;
import com.ppdai.canalmate.common.utils.CanalPropertyUtils;
import com.ppdai.canalmate.common.utils.CanalZKUtils;
import com.ppdai.canalmate.common.utils.DBUtils;
import com.ppdai.canalmate.common.utils.DateUtil;
import com.ppdai.canalmate.common.utils.P;
import com.ppdai.canalmate.common.utils.PropertiesUtils;
import com.ppdai.canalmate.common.utils.ReponseEnum;
import com.ppdai.canalmate.common.utils.ZKClient;
import com.ppdai.canalmate.common.utils.ZookeeperPathUtils;

import io.swagger.annotations.ApiParam;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class CanalGapService {
	
    Logger logger = LoggerFactory.getLogger(CanalGapService.class);

    @Autowired
    @Lazy
    @Qualifier("mysqlJdbcTemplate")
    private JdbcTemplate mysqlJdbcTemplate;//通过jdbcTemplate的方式操作数据库,可以和下面的mybatis的方式共存
    
    @Qualifier(value = "processMonitorService")
    @Autowired
    ProcessMonitorService processMonitorService;

    public List<DestinationsConfig> getDestinationsConfigList(Map<String,String> argsMap) {
    	String temp="select id, canal_id, destination_name, description, destination_configuration, "
    			+ "standby_configuration, inserttime, updatetime, isactive \r\n" + 
    			"from destinations_config where 1=1 and isactive=1 ";
        StringBuffer selectByParamSql = new StringBuffer(temp);
        String canalId=argsMap.get("canal_id");
        if (StringUtils.isNotBlank(canalId)) {
        	selectByParamSql.append(" and canal_id = ").append(Integer.parseInt(canalId)).append(" ");
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
            	dto.setIsactive(rs.getBoolean("isactive"));
            	
                resultList.add(dto);
            }
        });
        return resultList;
    }
    
    
    
 public List<CanalGapDto> listInstanceStatus(String canalId) {
        
    	List<CanalGapDto> instanceGapList = new ArrayList<CanalGapDto>();
    	//根据server id ，从数据库取对应的server
    	CanalServerConfig canalServerConfig=processMonitorService.selectCanalServerConfigByPrimaryKey(Long.valueOf(canalId));
    	//找到该server下所有的destination
    	List<DestinationsConfig> destinationsConfigList = new ArrayList<DestinationsConfig>();
    	Map<String,String> argsMap=new HashMap<String,String>();
    	if (StringUtils.isNotBlank(canalId)) {
    		argsMap.put("canal_id", canalId);
    	}
    	destinationsConfigList = getDestinationsConfigList(argsMap);
    	
    	//
    	String zkAddress=CanalPropertyUtils.getPropertyValueByKey(canalServerConfig.getCanalServerConfiguration(),"canal.zkServers");
    	
    	ZKClient test = new ZKClient(zkAddress);
    	
    	try {
    		//创建链接
        	test.createConnection();
        	
        	String destinationName,masterMysqlSlaveId,standbyMysqlSlaveId,masterAddress,dbUsername,dbPassword,destination,masterJournalName,masterPosition;
        	String cursorPath,jsonStr;
        	String destinationConfiguration,standbyConfiguration=null;
        	LogPosition logPosition=null;
        	//循环该instance，找对应des的Mysql位点和zk位点
        	for(DestinationsConfig destinationsConfig:destinationsConfigList) {
        		String positionGap=null,instanceJournalName=null,instancePosition=null,instanceTimestamp=null;
        		String mysqlSlaveId=null;//把master和slave的slaveid放在一起
        		destinationName=destinationsConfig.getDestinationName();
        		destinationConfiguration = destinationsConfig.getDestinationConfiguration();
        		standbyConfiguration = destinationsConfig.getStandbyConfiguration();
        		masterMysqlSlaveId=CanalPropertyUtils.getPropertyValueByKey(destinationConfiguration, "canal.instance.mysql.slaveId");
        		standbyMysqlSlaveId=CanalPropertyUtils.getPropertyValueByKey(standbyConfiguration, "canal.instance.mysql.slaveId");
        		if (StringUtils.isNotBlank(masterMysqlSlaveId)) {
        			mysqlSlaveId=masterMysqlSlaveId;
            	}
        		if (StringUtils.isNotBlank(standbyMysqlSlaveId)) {
        			mysqlSlaveId=mysqlSlaveId+","+standbyMysqlSlaveId;
            	}

        		masterAddress=CanalPropertyUtils.getPropertyValueByKey(destinationConfiguration, "canal.instance.master.address");
        		dbUsername=CanalPropertyUtils.getPropertyValueByKey(destinationConfiguration, "canal.instance.dbUsername");
        		dbPassword=CanalPropertyUtils.getPropertyValueByKey(destinationConfiguration, "canal.instance.dbPassword");
    	
        		//登录mysql查询mysql位点
        		Map<String,String> result=DBUtils.getDBMasterStatus(masterAddress,dbUsername,dbPassword);
        		masterJournalName=result.get("binlogFile");
        		masterPosition=result.get("binlogOffset");
        		StringBuilder commentSb=new StringBuilder();
        		String colour=null;
        		if(result.get("code").equals(ReponseEnum.SUCCEED.getResCode())) {
        			masterJournalName=result.get("binlogFile");
            		masterPosition=result.get("binlogOffset");
        		}else {
        			masterJournalName="";
            		masterPosition="";
            		commentSb.append("查询数据库地址为"+masterAddress+"的binlog失败,请检查数据库状态,");
            		colour=CanalConstants.RED;
        		}
        		//登录zk,查询canal client位点信息 /otter/canal/destinations/testdb01/1001/cursor
                cursorPath=ZookeeperPathUtils.getCursorPath(destinationName,CanalConstants.clientId);
                jsonStr = test.getNodeData(cursorPath, false);
                ZKDestinationBean zKDestinationBean=CanalZKUtils.getZKDestinationBeanFromZKAddressByDestinationName(zkAddress, destinationName);
                if(zKDestinationBean!=null) {//若该dest能从zk正确获取
                	logPosition = zKDestinationBean.getClientCursorLogPosition();
                	if(logPosition!=null) {
                		instanceJournalName=logPosition.getPostion().getJournalName();
                		instancePosition=String.valueOf(logPosition.getPostion().getPosition());
                		instanceTimestamp=String.valueOf(logPosition.getPostion().getTimestamp());
                	}else {
                		commentSb.append("查询destination为"+destinationName+"在zk的位点信息失败,"+zKDestinationBean.getStatusComment());
                		colour=CanalConstants.RED;
                	}
                }else {//若该dest不能从zk正确获取
                	commentSb.append("查询destination为"+destinationName+"在zk的位点信息失败,"+zKDestinationBean.getStatusComment());
            		colour=CanalConstants.RED;
                }
                
                Long GAP_HOUR_threshold=Long.parseLong(PropertiesUtils.getValue("gapHour"));
                Long GAP_MIN_threshold=Long.parseLong(PropertiesUtils.getValue("gapMin"));
                
                logger.debug("=====GAP_HOUR_threshold告警阈值:"+GAP_HOUR_threshold);
                logger.debug("=====GAP_MIN_threshold告警阈值:"+GAP_MIN_threshold);

                //最后总结判断
                if(!StringUtils.isBlank(colour)) {//若color不为空，说明前面赋值为红，说明已经有问题，不用判断
                	logger.debug("检查gap有问题，不用判断，直接标红,原因："+commentSb.toString());
                }else {//若没有赋值，检查gap
                	//计算gap
                	positionGap=String.valueOf(Long.valueOf(masterPosition)-Long.valueOf(instancePosition));//计算gap
                	Long destinationTimestamp=Long.valueOf(instanceTimestamp);
                	Long currentTimestamp=DateUtil.getUTCTimeFromCurrentDate();
                	Long gapHour=(currentTimestamp-destinationTimestamp)/1000/60/60;
                	Long gapMin=(currentTimestamp-destinationTimestamp)/1000/60;
                	logger.debug("gap_second:"+(currentTimestamp-destinationTimestamp)+",current_timestamp:"+currentTimestamp+",destination_timestamp:"+destinationTimestamp);
                	logger.debug("gap_hour:"+gapHour);
                	//先判断是否超过了小时级别告警，若超过小时级别，则直接告警，后面分钟级别不判断。若没超过小时级别，再判断是否超过分钟级别。
                	if(gapHour>GAP_HOUR_threshold) {
                		commentSb.append("client位点时间比当前时间已延迟"+gapHour+"小时,超过"+GAP_HOUR_threshold+"小时阈值,");
                		colour=CanalConstants.YELLOW;
                	}else if(gapMin>GAP_MIN_threshold) {
                		commentSb.append("client位点时间比当前时间已延迟"+gapMin+"分钟,超过"+GAP_MIN_threshold+"分钟阈值,");
                		colour=CanalConstants.YELLOW;
                	}else{
                		commentSb.append("正常,");
                		colour=CanalConstants.GREEN;//唯一能标注绿色正常的情况
                	}
                }
                //去掉最后的,逗号
                String comment=commentSb.toString();
                if(!StringUtils.isBlank(comment)) {
                	comment=comment.substring(0, comment.length()-1);
                }
                
                CanalGapDto dto=new CanalGapDto();
                dto.setDestnationName(destinationName);
                dto.setMysqlSlaveId(masterMysqlSlaveId);
                dto.setMasterAddress(masterAddress);
                dto.setMasterJournalName(masterJournalName);
                dto.setMasterPosition(masterPosition);
                dto.setInstanceJournalName(instanceJournalName);
                dto.setInstancePosition(instancePosition);
                dto.setInstanceTimestamp(instanceTimestamp);
                if(StringUtils.isNotBlank(instanceTimestamp)) {
                	dto.setInstanceTimestampStr(DateUtil.UTCTime2Date(instanceTimestamp));
                }
                dto.setPositionGap(positionGap);
                dto.setColour(colour);
                dto.setComment(comment);
                
                instanceGapList.add(dto);
        	}
    	}catch(Exception e) {
    		e.printStackTrace();
    	}finally {
    		test.releaseConnection();
    		
    	}
    	
    	

	    return instanceGapList;
    }
    
    
    
}
