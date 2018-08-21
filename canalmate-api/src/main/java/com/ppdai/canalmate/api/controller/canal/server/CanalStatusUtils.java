package com.ppdai.canalmate.api.controller.canal.server;
//package com.ppdai.console.api.controller.canal.server;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import org.apache.commons.lang.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Component;
//
//import com.ppdai.console.api.model.canal.server.CanalClientStatus;
//import com.ppdai.console.api.model.canal.server.CanalServerConfigShow;
//import com.ppdai.console.api.model.canal.server.ClientConfig;
//import com.ppdai.console.api.model.canal.server.DestinationsConfig;
//import com.ppdai.console.api.service.canal.JdbcTemplateService;
//import com.ppdai.console.api.service.canal.ProcessMonitorService;
//import com.ppdai.console.common.cons.CanalConstants;
//import com.ppdai.console.common.model.ZKDestinationBean;
//import com.ppdai.console.common.model.ZKDestinationClientRunningNode;
//import com.ppdai.console.common.utils.CanalPropertyUtils;
//import com.ppdai.console.common.utils.CanalZKUtils;
//import com.ppdai.console.common.utils.P;
//
///*
// * 封装canal的状态信息
// * */
//@EnableAutoConfiguration
//public class CanalStatusUtils {
//	
//    static Logger logger = LoggerFactory.getLogger(CanalStatusUtils.class);
//
//    @Qualifier(value = "processMonitorService")
//    @Autowired
//    private ProcessMonitorService processMonitorService;
//    
//    @Autowired
//    @Lazy
//    @Qualifier("mysqlJdbcTemplate")
//    private JdbcTemplate mysqlJdbcTemplate;//通过jdbcTemplate的方式操作数据库,可以和下面的mybatis的方式共存
//    
//    
////    @Autowired
////    @Lazy
////    @Qualifier("jdbcTemplateService")
////    private JdbcTemplateService jdbcTemplateService;
//    
//    @Qualifier(value = "jdbcTemplateService")
//    @Autowired
//    private JdbcTemplateService jdbcTemplateService;
//    
//    /*
//	 * 根据传入的一个canal server的id和该server在zk上所有destination列表，结合数据库配置信息，返回所有的canal client 信息。
//	 * */
//    public  List<CanalClientStatus> getCanalClientStatusBeanList(String canal_id,List<ZKDestinationBean> zKDestinationBeanList) {
//    	List<CanalClientStatus> canalClientStatusList=new ArrayList<CanalClientStatus>();
//    	Set<String> zkDestinationSet=new HashSet<String>();
//    	/* 
//    	 * 判断canal client的状态，分2步
//    	 * 第一步：先循环zk取的数据
//    	 * 把从zk取的数据做比较基准，数据都是zk存在的。
//    	 * 循环每个destination.
//    	 * 若该destination有client信息，且db中有该destination信息，说明与数据库信息一致，且有client在消费该destination，标绿
//    	 * 若该destination有client信息，但db中没有该destination信息，说明zk与数据库信息不一致，有可能数据库没配置该destination。标黄
//    	 * 若该destination没有client信息，说明没有client在消费该destination，有可能该client挂了。标红
//    	 * 
//    	 * 第二步：根据db的数据做比较基准，主要根据destinationName，因为这个字段唯一索引，与zk的destinationName一一对应。
//    	 * 若db中有该canalserver的destination的信息，但zk中没有，说明db中该destination有可能多余，需要删除。
//    	 * 
//    	 * */
//    	//第一步：先循环zk取的数据，在zk存在的情况下，判断是否与db里面匹配
//    	//循环每个destination
//    	for(ZKDestinationBean zKDestinationBean:zKDestinationBeanList) {
//    		//先从每一个destination取对应的数据库信息
//    		String destinationName=zKDestinationBean.getDestinationName();
//    		if(zKDestinationBean.getzKDestinationRunningNode()==null) {
//    			P.p("destinationName："+destinationName+"没有server运行它,有可能该destination已经摘除，zk信息过时，不计算，直接跳过");
//    			continue;
//    		}
//    		
//    		CanalClientStatus clientStatus=new CanalClientStatus();
//    		zkDestinationSet.add(destinationName);//放到map中，给后面的db判断准备数据
//    		clientStatus.setDestinationName(destinationName);
//    		
//    		//查询条件，该canalserver下的destination名为destinationName的记录
//    		Map<String,String> argsMap=new HashMap<String,String>();
//    		argsMap.put("destination_name", destinationName.trim());
//    		argsMap.put("canal_id", canal_id);
//    		//查询destination
//    		if(processMonitorService==null) {
//    			logger.error("=========processMonitorService is null");
//    		}
//    		DestinationsConfig destinationsConfig=processMonitorService.selectDestinationsConfigByArgs(argsMap);
//    		//根据destination id，查询client config信息，一个destination唯一对应一个client
//    		
//
//    		//从每个destination中取 cluster 节点的状态
//    		ZKDestinationClientRunningNode clientRunningNode=zKDestinationBean.getzKDestinationClientRunningNode();
//    		if(clientRunningNode!=null){//说明该destination有客户端消费
//    			//if(destinationsConfig!=null&&destinationList.size()>0) {//若数据库中有该destination的信息
//    			if(destinationsConfig!=null) {//若数据库中有该destination的信息
//    				StringBuilder comment=new StringBuilder();
//    				//取clientName
//    				argsMap.clear();
//    	    		argsMap.put("destination_id", String.valueOf(destinationsConfig.getId()));
//    	    		ClientConfig clientConfig=jdbcTemplateService.selectClientConfigByArgs(argsMap);
//    	    		String clientName="";
//    	    		if(clientConfig!=null) {
//    	    			clientName=clientConfig.getClientName();
//    	    			comment.append("destination:"+destinationName+"有client消费");
//    	    		}else {
//    	    			comment.append("destination:"+destinationName+"在数据库没有配置client或配置了多个client");
//    	    		}
//    	    		//取配置文件，从配置文件取对应的mysql的地址
//    	            String destinationConfiguration = destinationsConfig.getDestinationConfiguration();
//    	    		String mysqlAddress=CanalPropertyUtils.getPropertyValueByKey(destinationConfiguration, "canal.instance.master.address");
//    				String clientAddressPort=clientRunningNode.getAddress();
//    				clientStatus.setClientName(clientName);
//    				clientStatus.setMysqlAddress(mysqlAddress);
//    				clientStatus.setClientAddressPort(clientAddressPort);
//    				clientStatus.setColor(CanalConstants.GREEN);
//    				clientStatus.setCanalClientStatusCode(CanalConstants.RUNNING);
//    				clientStatus.setComment(comment.toString());
//    			}else {//若数据库中没有该destination的信息
//    				StringBuilder comment=new StringBuilder();
//    				comment.append("destination:"+destinationName+"有client消费,但db中没有该destination信息，说明zk与数据库信息不一致，可能数据库没配置该destination");
//    				String clientAddressPort=clientRunningNode.getAddress();
//    				clientStatus.setClientAddressPort(clientAddressPort);
//    				clientStatus.setColor(CanalConstants.YELLOW);
//    				clientStatus.setCanalClientStatusCode(CanalConstants.RUNNING);
//    				clientStatus.setComment(comment.toString());
//    			}
//    		}else {//若该destination没有client消费
//    			if(destinationsConfig!=null) {//若数据库中有该destination的信息
//    				StringBuilder comment=new StringBuilder();
//    				//取clientName
//    				argsMap.clear();
//    	    		argsMap.put("destination_id", String.valueOf(destinationsConfig.getId()));
//    	    		ClientConfig clientConfig=jdbcTemplateService.selectClientConfigByArgs(argsMap);
//    	    		String clientName="";
//    	    		if(clientConfig!=null) {
//    	    			clientName=clientConfig.getClientName();
//    	    			comment.append("destination为"+destinationName+"没有client消费");
//    	    		}else {
//    	    			comment.append("destination:"+destinationName+"没有client消费,数据库没有配置client或配置了多个client");
//    	    		}
//    	    		//取配置文件，从配置文件取对应的mysql的地址
//	    			String destinationConfiguration = destinationsConfig.getDestinationConfiguration();
//		    		String mysqlAddress=CanalPropertyUtils.getPropertyValueByKey(destinationConfiguration, "canal.instance.master.address");
//    				clientStatus.setClientName(clientName);
//		    		clientStatus.setMysqlAddress(mysqlAddress);
//	    			clientStatus.setClientAddressPort(null);
//	    			clientStatus.setColor(CanalConstants.RED);
//	    			clientStatus.setCanalClientStatusCode(CanalConstants.NOT_RUNNING);
//	    			clientStatus.setComment(comment.toString());
//    			}else {//若数据库中没有该destination的信息
//    				StringBuilder comment=new StringBuilder();
//    				comment.append("destination:"+destinationName+"没有client消费,且db中没有该destination信息，可能数据库配置该destination有误");
//    				clientStatus.setColor(CanalConstants.YELLOW);
//    				clientStatus.setCanalClientStatusCode(null);
//    				clientStatus.setComment(comment.toString());
//    			}
//    		}
//    		//把该destination对应的client放到列表
//    		canalClientStatusList.add(clientStatus);
//    		
//    	}
//    	
//    	//第二步：根据db的数据做比较基准，主要根据destinationName，因为这个字段唯一索引，与zk的destinationName一一对应(已放入zkDestinationSet)。
//		//查询条件，该canalserver下的所有destination
//		Map<String,String> argsMap=new HashMap<String,String>();
//		argsMap.put("canal_id", canal_id);
//		List<DestinationsConfig> destinationList=processMonitorService.selectDestinationConfigByArgs(argsMap);
//		for(DestinationsConfig destinationsConfig : destinationList) {
//			String destinationName=destinationsConfig.getDestinationName();
//			if(!zkDestinationSet.contains(destinationName)) {
//	    		CanalClientStatus clientStatus=new CanalClientStatus();
//	    		clientStatus.setClientAddressPort(null);
//    			clientStatus.setColor(CanalConstants.YELLOW);
//    			clientStatus.setComment("destination为"+destinationName+"在zk中不存在，可能数据库destination表配置错误");
//				
//        		canalClientStatusList.add(clientStatus);
//			}
//			
//		}
//		
//		return canalClientStatusList;
//    }
//    
//    
//	
//	/*
//	 * 重新封装从数据库中的这个list，根据zk的状态，填入bean的状态信息，并返回list
//	 * */
//    public static List<CanalServerConfigShow> getCanalServerStatusBeanList(List<CanalServerConfigShow> canalServerConfigShowList) {
//    	
//    	//循环当前页的canal serverlist，从zk取对应的状态
//    	for(CanalServerConfigShow canalServerConfig: canalServerConfigShowList) {
//    		//找到该server集群下有几台canal server运行
//        	String zkAddress=CanalPropertyUtils.getPropertyValueByKey(canalServerConfig.getCanalServerConfiguration(),"canal.zkServers");
//        	List<String> ipAndPortList=CanalZKUtils.getZKCanalServerListFromZKAddress(zkAddress);
//        	Set<String>  canalServerIpSet=new HashSet<String>();
//        	for(String ipAndPort:ipAndPortList) {
//        		String ip=ipAndPort.split(":")[0];
////        		P.p("zk canal server ip:"+ip);
//        		canalServerIpSet.add(ip);
//        	}
//        	//得到该master的canal.ip，先从配置文件取
//        	String master_canal_ip=CanalPropertyUtils.getPropertyValueByKey(canalServerConfig.getCanalServerConfiguration(),"canal.ip");
//        	String standby_canal_ip=CanalPropertyUtils.getPropertyValueByKey(canalServerConfig.getStandbyServerConfiguration(),"canal.ip");
//        	//若配置文件中没有（说明配置文件出错了），从数据库字段中取
//        	if(StringUtils.isBlank(master_canal_ip)) {
//        		master_canal_ip=canalServerConfig.getCanalServerHost();
//        	}
//        	if(StringUtils.isBlank(standby_canal_ip)) {
//        		standby_canal_ip=canalServerConfig.getStandbyServerHost();
//        	}
//        	
////        	P.p("master IP："+master_canal_ip+",standby_canal_ip:"+standby_canal_ip);
//        	//判断master是否运行，主要是设置StatusCode
//        	if(canalServerIpSet.contains(master_canal_ip)) {
//        		P.p("master RUNNING");
//        		canalServerConfig.setServerStatusCode(CanalConstants.RUNNING);
//        	}else {
//        		P.p("master NOT RUNNING");
//        		canalServerConfig.setServerStatusCode(CanalConstants.NOT_RUNNING);
//        	}
//        	//判断standby是否运行
//        	if(canalServerIpSet.contains(standby_canal_ip)) {
//        		P.p("standby RUNNING");
//        		canalServerConfig.setStandbyStatusCode(CanalConstants.RUNNING);
//        	}else {
//        		P.p("standby NOT RUNNING");
//        		canalServerConfig.setStandbyStatusCode(CanalConstants.NOT_RUNNING);
//        	}
//        	
//        	String canalServerPort=canalServerConfig.getCanalServerPort();
//        	String standbyServerPort=canalServerConfig.getStandbyServerPort();
//
//        	canalServerConfig.setCanalServerHostPort(master_canal_ip+":"+canalServerPort);
//        	canalServerConfig.setStandbyServerHostPort(standby_canal_ip+":"+standbyServerPort);
//    	}
//    	
//    	return canalServerConfigShowList;
//    	
//    }
//	
//    
//}
