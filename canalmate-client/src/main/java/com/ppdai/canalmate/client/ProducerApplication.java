package com.ppdai.canalmate.client;

import com.ppdai.canalmate.client.canal.CanalClient;
import com.ppdai.canalmate.client.conf.DBProperties;
import com.ppdai.canalmate.client.conf.PropertiesUtils;
import com.ppdai.canalmate.client.producer.Sender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProducerApplication implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(ProducerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class, args);
    }

    @Autowired
    private Sender sender;

    @Override
    public void run(String... strings) throws Exception {
        String clientName = strings[0];
        // 创建canal 客户端与server的链接
//       CanalClient canalClient = new CanalClient(clientName);
         String url=PropertiesUtils.getValue("url");
         String name=PropertiesUtils.getValue("driverName");
         String user=PropertiesUtils.getValue("username");
         String password=PropertiesUtils.getValue("password");
         System.out.println("==========clientName:"+clientName);
//         System.out.println("==========url:"+url);
//         System.out.println("==========name:"+name);
//         System.out.println("==========user:"+user);
//         System.out.println("==========password:"+password);
         
         CanalClient canalClient = new CanalClient();
        canalClient.setClientName(clientName);
        canalClient.setUrl(url);
        canalClient.setName(name);
        canalClient.setUser(user);
        canalClient.setPassword(password);
        canalClient.init();
        // 调整batchSize，处理binlog 数据
        canalClient.messageProcess(1000, sender);




    }

}

