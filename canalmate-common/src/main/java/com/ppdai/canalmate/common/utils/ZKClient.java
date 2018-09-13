package com.ppdai.canalmate.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;

import com.alibaba.fastjson.JSON;
import com.alibaba.otter.canal.protocol.position.LogPosition;
import com.ppdai.canalmate.common.cons.CanalConstants;
import com.ppdai.canalmate.common.model.ZKDestinationBean;
import com.ppdai.canalmate.common.model.ZKDestinationClientRunningNode;
import com.ppdai.canalmate.common.model.ZKDestinationClusterNode;
import com.ppdai.canalmate.common.model.ZKDestinationRunningNode;
import com.ppdai.canalmate.common.utils.P;

public class ZKClient implements Watcher{
    private static final Logger logger = Logger.getLogger(ZKClient.class);
    
    //定义session失效时间
    private  int my_session_timeout = CanalConstants.zk_session_timeout;
    //zookeeper服务器地址
//    private  String zookeeper_address = "IP:2181";
    private  String my_zookeeper_address;
    
    //ZooKeeper变量
    private ZooKeeper zk = null;
    //定义原子变量
    AtomicInteger seq = new AtomicInteger();
    //信号量设置，用于等待zookeeper连接建立之后，通知阻塞程序继续向下执行
    private CountDownLatch connectedSemaphore = new CountDownLatch(1);
    
    
    public ZKClient(String zookeeper_address,int session_timeout) {
		super();
		my_zookeeper_address = zookeeper_address;
		my_session_timeout = session_timeout;
	}
    
    public ZKClient(String zookeeper_address) {
		super();
		my_zookeeper_address = zookeeper_address;
		my_session_timeout = CanalConstants.zk_session_timeout;
	}


	public static void main(String[] args) throws InterruptedException {
//        String parentPath= "/otter/canal/destinations/testdb01/1001/cursor"; //父节点
//        String childrenPath = "/test/children"; //子节点
//        String zookeeper_address="IP:2181";
//        ZKClient test = new ZKClient(zookeeper_address);
//        //创建链接
//        test.createConnection();
//        String result = test.getNodeData(parentPath, false);
//        logger.debug("result:"+result);
//        //Position position = JSON.parseObject(result, Position.class);
//        //LogPosition position = JSON.parseObject(result, LogPosition.class);
//        //logger.debug(position);
//        
//    	List<String> childs = test.getChildren(ZookeeperPathUtils.CANAL_CLUSTER_ROOT_NODE, false);
//    	for(String node:childs) {
//    		logger.debug("=============server_node:"+node);
//    	}
//    	
//    	List<ZKDestinationBean> zKDestinationBeanList=new ArrayList<ZKDestinationBean>();
//    	childs = test.getChildren(ZookeeperPathUtils.DESTINATION_ROOT_NODE, false);
//    	for(String destinationName:childs) {
//    		logger.debug("=============destinationName:"+destinationName);
//    		String destinationPath=ZookeeperPathUtils.getDestinationPath(destinationName);
//    		logger.debug("destinationPath:"+destinationPath);
//    		
//    		ZKDestinationBean bean =new ZKDestinationBean();
//    		bean.setDestinationName(destinationName);
//    		bean.setDestinationPath(destinationPath);
//    		
//    		ZKDestinationRunningNode runningNode=new ZKDestinationRunningNode();
//    		
//    		String DESTINATION_RUNNING_NODE_PATH=ZookeeperPathUtils.getDestinationServerRunning(destinationName);
//    		logger.debug("=============DESTINATION_RUNNING_NODE:"+DESTINATION_RUNNING_NODE_PATH);
//    		result = test.getNodeData(DESTINATION_RUNNING_NODE_PATH, false);
//    		if (result == null) {
//    			logger.debug("=============没有server运行该dest,DESTINATION_RUNNING_NODE data:"+DESTINATION_RUNNING_NODE_PATH);
//    			runningNode=null;
//    		}else {
//    			logger.debug("===运行该dest的server 节点，DESTINATION_RUNNING_NODE data:"+result);
//    			runningNode=JSON.parseObject(result, ZKDestinationRunningNode.class);
//    		}
//    		bean.setzKDestinationRunningNode(runningNode);
//    		bean.setzKDestinationClientRunningNodePath(DESTINATION_RUNNING_NODE_PATH);
//    		
//    		List<ZKDestinationClusterNode> zKDestinationClusterNodeList=new ArrayList<ZKDestinationClusterNode>();
//    		
//    		String DESTINATION_CLUSTER_ROOT_PATH=ZookeeperPathUtils.getDestinationClusterRoot(destinationName);
//    		logger.debug("=============DESTINATION_CLUSTER_ROOT_PATH:"+DESTINATION_CLUSTER_ROOT_PATH);
//        	childs = test.getChildren(DESTINATION_CLUSTER_ROOT_PATH, false);
//        	for(String addressPort:childs) {
//        		logger.debug("===DESTINATION_CLUSTER_ROOT data:"+addressPort);
//        		ZKDestinationClusterNode zKDestinationClusterNode=new ZKDestinationClusterNode();
//        		zKDestinationClusterNode.setAddressPort(addressPort);
//        		if(runningNode!=null&&runningNode.getAddress().trim().equals(addressPort)) {
//        			zKDestinationClusterNode.setActive(true);
//        		}else {
//        			zKDestinationClusterNode.setActive(false);
//        		}
//        		zKDestinationClusterNodeList.add(zKDestinationClusterNode);
//        	}
//        	bean.setzKDestinationClusterNodePath(DESTINATION_CLUSTER_ROOT_PATH);
//        	bean.setzKDestinationClusterNodeList(zKDestinationClusterNodeList);
//        	
//        	ZKDestinationClientRunningNode zKDestinationClientRunningNode=new ZKDestinationClientRunningNode();
//    		String clientRunningNodePath=ZookeeperPathUtils.getDestinationClientRunning(destinationName,CanalConstants.clientId);
//    		logger.debug("=============clientRunningPath:"+clientRunningNodePath);
//    		result = test.getNodeData(clientRunningNodePath, false);
//    		if (result == null) {
//    			logger.debug("=============没有client消费该dest,clientRunningPath:"+clientRunningNodePath);
//    			zKDestinationClientRunningNode=null;
//    		}else {
//    			logger.debug("===运行该dest的server 节点，clientRunningPath data:"+result);
//    			zKDestinationClientRunningNode=JSON.parseObject(result, ZKDestinationClientRunningNode.class);
//    		}
//    		bean.setzKDestinationClientRunningNode(zKDestinationClientRunningNode);
//    		
//    		zKDestinationBeanList.add(bean);
//    		logger.debug("=============OVER=============");
//    	}
//    	
//        //释放链接
//        Thread.sleep(1000);
//        test.releaseConnection();
        
//    	DESTINATION_ROOT_NODE
        
        //Position position=JsonUtils.unmarshalFromByte(data, Position.class);
 
//        boolean isSuccess = test.createNode(parentPath, "abc", Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//        if (isSuccess) {
//            //读取数据
//            String result = test.getNodeData(parentPath, true);
//            logger.debug("更新前数据：" + result);
//            
//            //更新数据
//            isSuccess = test.updateNode(parentPath, String.valueOf(System.currentTimeMillis()));
//            if(isSuccess){
//                logger.debug("更新后数据：" + test.getNodeData(parentPath, true));
//            }
//            
//            // 创建子节点
//            isSuccess = test.createNode(childrenPath, String.valueOf(System.currentTimeMillis()), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//            if(isSuccess){
//                test.updateNode(childrenPath, String.valueOf(System.currentTimeMillis()));
//            }
//            
//            //读取子节点
//            List<String> childrenList = test.getChildren(parentPath, true);
//            if(childrenList!=null && !childrenList.isEmpty()){
//                for(String children : childrenList){
//                    System.out.println("子节点：" + children);
//                }
//            }
//        }
//        
//        Thread.sleep(1000);
//        //创建临时有序子节点
//       test.createNode(childrenPath, String.valueOf(System.currentTimeMillis()), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
//       test.createNode(childrenPath, String.valueOf(System.currentTimeMillis()), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
//       test.createNode(childrenPath, String.valueOf(System.currentTimeMillis()), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
//        
//        // 读取子节点，并删除
//        List<String> childrenList = test.getChildren(parentPath, true);
//        if (childrenList != null && !childrenList.isEmpty()) {
//            for (String children : childrenList) {
//                System.out.println("子节点：" + children);
//                test.deleteNode(parentPath + "/" + children);
//            }
//        }
//
//        //删除父节点 
//        if (test.exists(childrenPath, false) != null) {
//            test.deleteNode(childrenPath);
//        }
        

    }
    
 
    
    /**
     * 创建节点
     * 
     * @param path 节点路径
     * @param data 数据内容
     * @param acl 访问控制列表
     * @param createMode znode创建类型
     * @return
     */
    public boolean createNode(String path, String data, List<ACL> acl, CreateMode createMode) {
        try {
            //设置监控(由于zookeeper的监控都是一次性的，所以每次必须设置监控)
            exists(path, true);
            String resultPath = this.zk.create(path, data.getBytes(), acl, createMode);
            logger.debug(String.format("节点创建成功，path: %s，data: %s", resultPath, data));
        } catch (Exception e) {
            logger.error("节点创建失败", e);
            return false;
        }
        
        return true;
    }
    
    /**
     * 更新指定节点数据内容
     * 
     * @param path 节点路径
     * @param data 数据内容
     * @return
     */
    public boolean updateNode(String path, String data) {
        try {
            Stat stat = this.zk.setData(path, data.getBytes(), -1);
            logger.debug("更新节点数据成功，path：" + path + ", stat: " + stat);
        } catch (Exception e) {
            logger.error("更新节点数据失败", e);
            return false;
        }
        
        return true;
    }
    
    /**
     * 删除指定节点
     * 
     * @param path
     *            节点path
     */
    public void deleteNode(String path) {
        try {
            this.zk.delete(path, -1);
            logger.debug("删除节点成功，path：" + path);
        } catch (Exception e) {
            logger.error("删除节点失败", e);
        }
    }
    

    
    /**
     * 读取节点数据
     * 
     * @param path 节点路径
     * @param needWatch 是否监控这个目录节点，这里的 watcher是在创建ZooKeeper实例时指定的watcher
     * @return
     */
    public String getNodeData(String path, boolean needWatch) {
        try {
            Stat stat = exists(path, needWatch);
            if(stat != null){
                return new String(this.zk.getData(path, needWatch, stat));
            }
        } catch (Exception e) {
            logger.error("读取节点数据内容失败", e);
        }
        
        return null;
    }
    
    /**
     * 获取子节点
     * 
     * @param path 节点路径
     * @param needWatch  是否监控这个目录节点，这里的 watcher是在创建ZooKeeper实例时指定的watcher
     * @return
     */
    public List<String> getChildren(String path, boolean needWatch) {
        try {
            return this.zk.getChildren(path, needWatch);
        } catch (Exception e) {
        	logger.error("获取子节点失败,path:"+path);
//            logger.error("获取子节点失败", e);
            return null;
        }
    }
    
    
    /**
     * 判断znode节点是否存在
     * 
     * @param path 节点路径
     * @param needWatch 是否监控这个目录节点，这里的 watcher是在创建ZooKeeper实例时指定的watcher
     * @return
     */
    public Stat exists(String path, boolean needWatch) {
        try {
            return this.zk.exists(path, needWatch);
        } catch (Exception e) {
            logger.error("判断znode节点是否存在发生异常", e);
        }
        
        return null;
    }
    
    /**
     * 创建ZK连接
     * 
     * @param connectAddr
     * @param sessionTimeout
     */
    public void createConnection() {
        this.releaseConnection();
        try {
            zk = new ZooKeeper(my_zookeeper_address, my_session_timeout, this);
            logger.debug("开始连接ZK服务器...");
            
            //zk连接未创建成功进行阻塞
            connectedSemaphore.await();
        } catch (Exception e) {
            logger.error("ZK连接创建失败", e);
        }
    }
    
    /**
     * 关闭ZK连接
     */
    public void releaseConnection() {
        if (this.zk != null) {
            try {
                this.zk.close();
                logger.debug("ZK连接关闭成功");
            } catch (InterruptedException e) {
                logger.error("ZK连接关闭失败", e);
            }
        }
    }
    
    public void process(WatchedEvent event) {
        logger.debug("进入process()方法...event = " + event);
        
        if (event == null) {
            return;
        }
        
        KeeperState keeperState = event.getState(); // 连接状态
        EventType eventType = event.getType(); // 事件类型
        String path = event.getPath(); // 受影响的path
        
        String logPrefix = "【Watcher-" + this.seq.incrementAndGet() + "】";
        logger.debug(String.format("%s收到Watcher通知...", logPrefix));
        logger.debug(String.format("%s连接状态：%s", logPrefix, keeperState));
        logger.debug(String.format("%s事件类型：%s", logPrefix, eventType));
        logger.debug(String.format("%s受影响的path：%s", logPrefix, path));
        
        if (KeeperState.SyncConnected == keeperState) {
            if (EventType.None == eventType) {
                // 成功连接上ZK服务器
                logger.debug(logPrefix + "成功连接上ZK服务器...");
                connectedSemaphore.countDown();
                
            } else if (EventType.NodeCreated == eventType) {
                // 创建节点
                logger.debug(logPrefix + "节点创建");
                this.exists(path, true);
                 
            } else if (EventType.NodeDataChanged == eventType) {
                // 更新节点
                logger.debug(logPrefix + "节点数据更新");
                logger.debug(logPrefix + "数据内容: " + this.getNodeData(path, true));
                
            } else if (EventType.NodeChildrenChanged == eventType) {
                // 更新子节点
                logger.debug(logPrefix + "子节点变更");
                logger.debug(logPrefix + "子节点列表：" + this.getChildren(path, true));
            
            } else if (EventType.NodeDeleted == eventType) {
                // 删除节点
                logger.debug(logPrefix + "节点 " + path + " 被删除");
            }
             
        } else if (KeeperState.Disconnected == keeperState) {
            logger.debug(logPrefix + "与ZK服务器断开连接");
        } else if (KeeperState.AuthFailed == keeperState) {
            logger.debug(logPrefix + "权限检查失败");
        } else if (KeeperState.Expired == keeperState) {
            logger.debug(logPrefix + "会话失效");
        }
        
    }
    
}
