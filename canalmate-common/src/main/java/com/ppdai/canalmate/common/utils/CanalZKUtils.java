package com.ppdai.canalmate.common.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.otter.canal.protocol.position.LogPosition;
import com.ppdai.canalmate.common.cons.CanalConstants;
import com.ppdai.canalmate.common.model.ZKDestinationBean;
import com.ppdai.canalmate.common.model.ZKDestinationClientRunningNode;
import com.ppdai.canalmate.common.model.ZKDestinationClusterNode;
import com.ppdai.canalmate.common.model.ZKDestinationRunningNode;

/*
 * 读取zk里面的canal信息，并封装成可读的形式
 * */
public class CanalZKUtils {
	
    static Logger logger = LoggerFactory.getLogger(CanalZKUtils.class);
    
//    public static void main(String[] args) {
//    	String zkAddress="IP:2181";
//    	List<ZKDestinationBean> zKDestinationBeanList=getZKDestinationBeanListFromZKAddress(zkAddress);
//    	
//    	for(ZKDestinationBean zKDestinationBean:zKDestinationBeanList) {
//    		String destinationName=zKDestinationBean.getDestinationName();
//    		if(zKDestinationBean.getzKDestinationRunningNode()==null) {
//    			logger.debug("destinationName："+destinationName+"没有server运行它，不计算，直接跳过");
//    			continue;
//    		}
//    	}
//    }

	
	/*
     *根据传入的zk地址，查询该zk对应的整个canal server的集群列表。
	 * [zk: localhost:2181(CONNECTED) 2] ls /otter/canal/cluster
	 *	[IP:11111]
	 * */
    public static List<String> getZKCanalServerListFromZKAddress(String zkAddress) {
    	List<String> zKCanalServerList=new ArrayList<String>();
    	String result=new String();
// 		P.p("=============zkAddress:"+zkAddress);

    	ZKClient test = new ZKClient(zkAddress);
    	try {
    		test.createConnection();
    		List<String> canalServerList = test.getChildren(ZookeeperPathUtils.CANAL_CLUSTER_ROOT_NODE, false);
    		for(String ipAndPort:canalServerList) {
//    			P.p("=============server_node:"+ipAndPort);
    			zKCanalServerList.add(ipAndPort);
    		}
    	}catch(Exception e) {
    		e.printStackTrace();
    	}finally {
    		test.releaseConnection();
    	}
        
     	return zKCanalServerList;
    }
	
	/*
	 * 一组canal server有2个canal server，一个active,一个standby，对应一个zk上的地址。
	 * 该方法传入一个zk地址，得到该地址下该一组canal server(一个active,一个standby)对应的一个ZKDestinationBean的列表
	 * 列表的每个ZKDestinationBean，对应一个该canal server 对外服务的 destination,和相关的canal server ，和相关的client node信息
	 * 这个方法重要。
	 * */
    public static List<ZKDestinationBean> getZKDestinationBeanListFromZKAddress(String zkAddress) {
    	List<ZKDestinationBean> zKDestinationBeanList=new ArrayList<ZKDestinationBean>();
    	String result=new String();
    	
    	ZKClient test = new ZKClient(zkAddress);
        
    	try {
    		test.createConnection();
    		//获取该zk下的server下所有的destination
    		List<String> destinationList = test.getChildren(ZookeeperPathUtils.DESTINATION_ROOT_NODE, false);
    		for(String destinationName:destinationList) {
    			ZKDestinationBean bean =getZKDestinationBeanFromZKAddressByDestinationName(zkAddress,destinationName);
    			zKDestinationBeanList.add(bean);
    		}
    		
		}catch(Exception e) {
    		e.printStackTrace();
    	}finally {
    		test.releaseConnection();
    	}
        
        return zKDestinationBeanList;
    }
    
    
    /*
     * 根据传入的zk地址和一个destinameName。
     * 返回： 该destination对应的 ZKDestinationBean 对象
     * 该 ZKDestinationBean 包括：
     * 1：正在运行该destination的server节点 , get /otter/canal/destinations/ppdai_user/running
     * 2：部署该destination对应的cluster列表 , ls /otter/canal/destinations/ppdai_user/cluster
     * 3：消费该dest对应的canal client运行在哪台节点上的信息， get /otter/canal/destinations/ppdai_user/1001/running
     * 4：该dest对应的client在zk的消费位点信息，get /otter/canal/destinations/ppdai_user/1001/cursor
	 * */
    public static ZKDestinationBean getZKDestinationBeanFromZKAddressByDestinationName(String zkAddress,String destinationName) {
    	ZKDestinationBean zKDestinationBean =new ZKDestinationBean();
    	zKDestinationBean.setIsStatusOK(false);//默认为false，若能正确获取，后面置位true
    	StringBuilder statusCommentSb=new StringBuilder();
    	ZKClient test = new ZKClient(zkAddress);
    	ZKDestinationBean zKDestinationBeanTemp =new ZKDestinationBean();
    	try {
            test.createConnection();
     		String destinationPath=ZookeeperPathUtils.getDestinationPath(destinationName);//该destination对应的path
     		
     		zKDestinationBeanTemp.setDestinationName(destinationName);
     		zKDestinationBeanTemp.setDestinationPath(destinationPath);
     		
     		//1：查询正在运行该destination的server节点
     		ZKDestinationRunningNode runningNode=new ZKDestinationRunningNode();
     		//该destination运行在哪个canal server上。即：处在running状态的该destination的canal server,另一个可能是standby
     		String DESTINATION_RUNNING_NODE_PATH=ZookeeperPathUtils.getDestinationServerRunning(destinationName);
//         	P.p("=============DESTINATION_RUNNING_NODE:"+DESTINATION_RUNNING_NODE_PATH);
     		String result = test.getNodeData(DESTINATION_RUNNING_NODE_PATH, false);
     		if (result == null) {
//         			P.p("=============没有server运行该dest,DESTINATION_RUNNING_NODE data:"+DESTINATION_RUNNING_NODE_PATH);
     			statusCommentSb.append("没有server运行该dest:"+destinationName);
     			runningNode=null;
     		}else {
//         			P.p("===运行该dest的server 节点，DESTINATION_RUNNING_NODE data:"+result);
     			runningNode=JSON.parseObject(result, ZKDestinationRunningNode.class);
     		}
     		zKDestinationBeanTemp.setzKDestinationRunningNode(runningNode);
     		zKDestinationBeanTemp.setzKDestinationClientRunningNodePath(DESTINATION_RUNNING_NODE_PATH);
     		
     		//2：查询部署该destination对应的cluster列表，/otter/canal/destinations/dest_01/cluster
     		List<ZKDestinationClusterNode> zKDestinationClusterNodeList=new ArrayList<ZKDestinationClusterNode>();
     		String DESTINATION_CLUSTER_ROOT_PATH=ZookeeperPathUtils.getDestinationClusterRoot(destinationName);
//         	P.p("=============DESTINATION_CLUSTER_ROOT_PATH:"+DESTINATION_CLUSTER_ROOT_PATH);
     		List<String> destinationCanalServerList  = test.getChildren(DESTINATION_CLUSTER_ROOT_PATH, false);
         	for(String addressPort:destinationCanalServerList) {
//             		P.p("===DESTINATION_CLUSTER_ROOT data:"+addressPort);
         		ZKDestinationClusterNode zKDestinationClusterNode=new ZKDestinationClusterNode();
         		zKDestinationClusterNode.setAddressPort(addressPort);
         		if(runningNode!=null&&runningNode.getAddress().trim().equals(addressPort)) {
         			zKDestinationClusterNode.setActive(true);
         		}else {
         			zKDestinationClusterNode.setActive(false);
         		}
         		zKDestinationClusterNodeList.add(zKDestinationClusterNode);
         	}
         	zKDestinationBeanTemp.setzKDestinationClusterNodePath(DESTINATION_CLUSTER_ROOT_PATH);
         	zKDestinationBeanTemp.setzKDestinationClusterNodeList(zKDestinationClusterNodeList);
         	
         	//3：获取消费该dest对应的canal client运行在哪台节点上的信息
         	ZKDestinationClientRunningNode zKDestinationClientRunningNode=new ZKDestinationClientRunningNode();
     		String clientRunningNodePath=ZookeeperPathUtils.getDestinationClientRunning(destinationName,CanalConstants.clientId);
//         		P.p("=============clientRunningPath:"+clientRunningNodePath);
     		result = test.getNodeData(clientRunningNodePath, false);
     		if (result == null) {
//         			P.p("=============没有client消费该dest,clientRunningPath:"+clientRunningNodePath);
     			statusCommentSb.append("没有client消费该dest:"+destinationName);
     			zKDestinationClientRunningNode=null;
     		}else {
//         			P.p("===运行该dest的server 节点，clientRunningPath data:"+result);
     			zKDestinationClientRunningNode=JSON.parseObject(result, ZKDestinationClientRunningNode.class);
     		}
     		zKDestinationBeanTemp.setzKDestinationClientRunningNodePath(clientRunningNodePath);
     		zKDestinationBeanTemp.setzKDestinationClientRunningNode(zKDestinationClientRunningNode);
     		
    		//4：查询该dest对应的client在zk的消费位点信息，例子： /otter/canal/destinations/testdb01/1001/cursor
     		LogPosition position=null;
            String clientCursorPath=ZookeeperPathUtils.getCursorPath(destinationName,CanalConstants.clientId);
            result = test.getNodeData(clientCursorPath, false);
//            P.p("====clientCursorPath:"+clientCursorPath+",result:"+result);
     		if (result == null) {
     			statusCommentSb.append(",获取dest:"+destinationName+"在zk的消费位点失败,clientCursorPath:"+clientCursorPath);
     			position=null;
     		}else {
     			position=JSON.parseObject(result, LogPosition.class);
     		}
     		zKDestinationBeanTemp.setClientCursorLogPositionPath(clientCursorPath);
     		zKDestinationBeanTemp.setClientCursorLogPosition(position);
     		
     		//若最后执行到这里了，说明没有报错，则赋值。
     		zKDestinationBeanTemp.setIsStatusOK(true);
     		zKDestinationBeanTemp.setStatusComment(statusCommentSb.toString());
     		zKDestinationBean=zKDestinationBeanTemp;
    	} catch (Exception e) {
//            e.printStackTrace();
     		zKDestinationBeanTemp.setIsStatusOK(false);
     		zKDestinationBeanTemp.setStatusComment(statusCommentSb.toString());
    		logger.error("获取zk数据出错");
        }finally {
        	test.releaseConnection();
        }
    	
        return zKDestinationBean;
    }
    
    
}
