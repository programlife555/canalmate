package com.ppdai.canalmate.client.canal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.Message;
import com.ppdai.canalmate.client.EntryProcess;
import com.ppdai.canalmate.client.producer.Sender;

public class CanalClient {

    public CanalConnector connector;
    
    private String url;
    private String name;
    private String user;
    private String password;
    
    public Connection conn = null;
    public PreparedStatement pst = null;
    
    
    private String destination_name;
    private String zk_servers;
    private String filter;
    private String clientName;


    // 此构造方式适用于生产环境（支持Server HA）
    public void init() {
        //this.clientName=clientName;
        // 直接通过zookeeper中的destination running节点，获取当前服务的工作节点，来建立链接
        String clientSql = "select destination_name,zk_servers,filter from destinations_config dc left join client_config cc on cc.destination_id=dc.id  where cc.client_name='"+clientName+"'";
        long t1=System.currentTimeMillis();
        try{
            Class.forName(name);
            conn = DriverManager.getConnection(url, user, password);
            pst = conn.prepareStatement(clientSql);
            ResultSet ret = pst.executeQuery();
            while (ret.next()){
                destination_name = ret.getString(1);
                zk_servers = ret.getString(2);
                filter = ret.getString(3);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
        	try {
        		this.conn.close();
        		this.pst.close();
        	}catch(Exception e) {
                e.printStackTrace();
            }
        }
        
        
        //获取canal连接
        this.connector = CanalConnectors.newClusterConnector(
                zk_servers,
                destination_name, "", "");

        connector.connect();
        connector.subscribe(filter); // 监听单独的表 [可以使用正则进行过滤],现在又有filter没有设置[为了能添加表后立即生效，改为了服务端canal.instance.filter.regex设置]
//      connector.subscribe(".*\\..*"); // 监听全部库表
        connector.rollback();
        long t2=System.currentTimeMillis();
        System.out.println("========newClusterConnector is running ! cost:"+(t2-t1)/1000+"s");
    }

    public void messageProcess(int batchSize, Sender sender){
        int emptyCount = 0;
        while(true) {
            // 获取指定数量的数据
            Message message = connector.getWithoutAck(batchSize);
            long batchId = message.getId(); // 当本次返回数据为空时， 返回空包且batchId=-1，避免生成batchId，浪费性能，详见源码CanalServerWithEmbedded
            int size = message.getEntries().size();
            if (batchId == -1 || size == 0) {
                emptyCount++;
                if (emptyCount >= 86400) {
                    System.out.println("empty count : (24 hours)" + emptyCount);
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
            } else {
                emptyCount = 0;
                EntryProcess entryProcess = new EntryProcess(message.getEntries(), sender);
                entryProcess.process();
            }
            //canal server在接收了客户端的ack后，就会记录客户端提交的最后位点，如果canal client没有提交位点，则下一次canal client启动的时候  会将最后记录的位点把日志重新推送过来，直到canal client提交ack确认为止。
            connector.ack(batchId); // 提交确认
        }
    }

    public void disConnect(){
        try{
            connector.disconnect();
        }catch (Exception e){
            //
        }
    }

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}



}
