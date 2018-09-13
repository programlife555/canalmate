package com.ppdai.canalmate.common.model;

import java.util.List;

import com.alibaba.otter.canal.protocol.position.LogPosition;

/*
 * 描述一个destination在zk中的状态信息
 * [zk: localhost:2181(CONNECTED) 139] ls /otter/canal/destinations/ppdai_user
	[running, cluster, 1001]
 * 基本结构：
 * ZKDestinationBean
 * 		destinationName  destination名称，对应canal/conf下面的目录
 * 		destinationPath  该destination在zk中的path
 * 		zKDestinationClusterNodePath   该destination对应的canal server 集群，对应zk的路径 ：  ls /otter/canal/destinations/ppdai_user/cluster ，输出：[IP:11111, IP:11111]
 * 		ZKDestinationRunningNode  zk命令：get /otter/canal/destinations/ppdai_user/running ，输出：{"active":true,"address":"IP:11111","cid":2}
 * 			cid
 * 			address
 * 			active
 * 		List<ZKDestinationClusterNode>    zk命令 ：ls /otter/canal/destinations/ppdai_user/cluster ，输出：[IP:11111, IP:11111]
 * 			ZKDestinationClusterNode 
 * 				addressPort   对应 ： IP:11111
 * 				active        程序计算，zk没有该状态，表示该canal server 是否是active
 * 			.....        第二个 canal server
 * 		zKDestinationClientRunningNodePath   对应zk路径 /otter/canal/destinations/ppdai_user/running
 * 		ZKDestinationClientRunningNode   消费该destination的client的信息，zk命令：get /otter/canal/destinations/ppdai_user/running ，输出：{"active":true,"address":"IP:11111","cid":2}
 * 			clientId
 * 			address
 * 			active
 *      clientCursorLogPositionPath    对应zk:/otter/canal/destinations/ppdai_user/1001/cursor
 *      clientCursorLogPosition        get /otter/canal/destinations/ppdai_user/1001/cursor 
 *									   {"@type":"com.alibaba.otter.canal.protocol.position.LogPosition","identity":{"slaveId":-1,"sourceAddress":{"address":"IP","port":3403}},"postion":{"included":false,"journalName":"mysql-bin.000008","position":43508043,"serverId":123456,"timestamp":1528098351000}}
 * 
 * */
public class ZKDestinationBean {
    private String destinationName;

    private String destinationPath;//该dest在zk对应的path
    
    private ZKDestinationRunningNode zKDestinationRunningNode;//1：正在运行该destination的server节点 , get /otter/canal/destinations/ppdai_user/running
    private String zKDestinationClusterNodePath;
    
    private List<ZKDestinationClusterNode> zKDestinationClusterNodeList;//2: 部署该destination对应的cluster列表 , ls /otter/canal/destinations/ppdai_user/cluster

    private ZKDestinationClientRunningNode zKDestinationClientRunningNode;//3：消费该dest对应的canal client运行在哪台节点上的信息， get /otter/canal/destinations/ppdai_user/1001/running
    private String zKDestinationClientRunningNodePath;
    
    private LogPosition clientCursorLogPosition;//4：该dest对应的client在zk的消费位点信息，get /otter/canal/destinations/ppdai_user/1001/cursor
    private String clientCursorLogPositionPath;
    
    private Boolean isStatusOK=false;//该Destination获取的时候，是否能正确获取该Destination的信息，由后来的程序填写。
    private String statusComment;//描述上述状态的是否OK的详细信息

    
	public String getStatusComment() {
		return statusComment;
	}
	public void setStatusComment(String statusComment) {
		this.statusComment = statusComment;
	}
	public String getDestinationName() {
		return destinationName;
	}
	public String getClientCursorLogPositionPath() {
		return clientCursorLogPositionPath;
	}
	public void setClientCursorLogPositionPath(String clientCursorLogPositionPath) {
		this.clientCursorLogPositionPath = clientCursorLogPositionPath;
	}
	public LogPosition getClientCursorLogPosition() {
		return clientCursorLogPosition;
	}
	public void setClientCursorLogPosition(LogPosition clientCursorLogPosition) {
		this.clientCursorLogPosition = clientCursorLogPosition;
	}
	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}
	public String getDestinationPath() {
		return destinationPath;
	}
	public Boolean getIsStatusOK() {
		return isStatusOK;
	}
	public void setIsStatusOK(Boolean isStatusOK) {
		this.isStatusOK = isStatusOK;
	}
	public void setDestinationPath(String destinationPath) {
		this.destinationPath = destinationPath;
	}
	public ZKDestinationRunningNode getzKDestinationRunningNode() {
		return zKDestinationRunningNode;
	}
	public void setzKDestinationRunningNode(ZKDestinationRunningNode zKDestinationRunningNode) {
		this.zKDestinationRunningNode = zKDestinationRunningNode;
	}
	public String getzKDestinationClusterNodePath() {
		return zKDestinationClusterNodePath;
	}
	public void setzKDestinationClusterNodePath(String zKDestinationClusterNodePath) {
		this.zKDestinationClusterNodePath = zKDestinationClusterNodePath;
	}
	public List<ZKDestinationClusterNode> getzKDestinationClusterNodeList() {
		return zKDestinationClusterNodeList;
	}
	public void setzKDestinationClusterNodeList(List<ZKDestinationClusterNode> zKDestinationClusterNodeList) {
		this.zKDestinationClusterNodeList = zKDestinationClusterNodeList;
	}
	public String getzKDestinationClientRunningNodePath() {
		return zKDestinationClientRunningNodePath;
	}
	public void setzKDestinationClientRunningNodePath(String zKDestinationClientRunningNodePath) {
		this.zKDestinationClientRunningNodePath = zKDestinationClientRunningNodePath;
	}
	public ZKDestinationClientRunningNode getzKDestinationClientRunningNode() {
		return zKDestinationClientRunningNode;
	}
	public void setzKDestinationClientRunningNode(ZKDestinationClientRunningNode zKDestinationClientRunningNode) {
		this.zKDestinationClientRunningNode = zKDestinationClientRunningNode;
	}

    
    
}