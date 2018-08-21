package com.ppdai.canalmate.api.service.canal.server.impl;

import com.ppdai.canalmate.api.dao.canal.server.ClientConfigMapper;
import com.ppdai.canalmate.api.model.canal.server.ClientConfig;
import com.ppdai.canalmate.api.service.canal.server.ClientConfigService;
import com.ppdai.canalmate.common.utils.RmtShellExecutor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service(value = "ClientConfigService")
public class ClientConfigServiceImpl implements ClientConfigService{

    @Autowired
    private ClientConfigMapper clientConfigMapper;

    @Override
    public void addClient(ClientConfig clientConfig){
        clientConfig.setDestinationId(clientConfigMapper.selectDestinationId(clientConfig.getDestinationName()));
        clientConfigMapper.insert(clientConfig);
    }

    @Override
    public void deleteClient(String clientName){
        clientConfigMapper.deleteByClientName(clientName);
    }

    @Override
    public Map<String,Object> selectClientOldConfig(String clientName){
        return  clientConfigMapper.selectClientOldConfig(clientName);
    }

    @Override
    public void updateClient(ClientConfig clientConfig){
        clientConfigMapper.updateByClientName(clientConfig);
    }


    @Override
    public Map<String,Object>  startClient(String clientName,String type) {
        Map<String,Object> config = clientConfigMapper.selectClientConfig(clientName);
        String destinationName = (String) config.get("destinationName");
        String host = (String) config.get("host");
        String path = (String) config.get("path");
        String startClientCmd = "export PATH=$PATH:/usr/local/java/bin && /bin/bash " + path + "/bin/startup.sh " + clientName;
        Map<String,Object> resMap = new HashMap<>();
        if ("master".equals(type)){
            RmtShellExecutor exe = new RmtShellExecutor(host, "hadoop", "hadoop",23245);
            try {
                resMap = exe.exec(startClientCmd);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//        else if (type.equals("standby")){
//            RmtShellExecutor exe = new RmtShellExecutor(standbyServerHost, "hadoop", "hadoop",23245);
//            try {
//                resMap = exe.exec(startCanalCmd);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        return resMap;
    }

    @Override
    public Map<String,Object>  stopClient(String clientName,String type) {
        Map<String,Object> config = clientConfigMapper.selectClientConfig(clientName);
        String destinationName = (String) config.get("destinationName");
        String host = (String) config.get("host");
        String path = (String) config.get("path");
        String stopClientCmd = "/bin/bash " + path + "/bin/stop.sh " + clientName;
        Map<String,Object> resMap = new HashMap<>();
        if ("master".equals(type)){
            RmtShellExecutor exe = new RmtShellExecutor(host, "hadoop", "hadoop",23245);
            try {
                resMap = exe.exec(stopClientCmd);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//        else if (type.equals("standby")){
//            RmtShellExecutor exe = new RmtShellExecutor(standbyServerHost, "hadoop", "hadoop",23245);
//            try {
//                resMap = exe.exec(startCanalCmd);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        return resMap;

    }


}
