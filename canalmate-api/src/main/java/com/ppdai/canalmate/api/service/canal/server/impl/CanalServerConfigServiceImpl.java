package com.ppdai.canalmate.api.service.canal.server.impl;

import com.alibaba.fastjson.JSONObject;
import com.ppdai.canalmate.api.dao.canal.server.CanalServerConfigMapper;
import com.ppdai.canalmate.api.model.canal.server.CanalServerConfig;
import com.ppdai.canalmate.api.service.canal.server.CanalServerConfigService;
import com.ppdai.canalmate.common.utils.*;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.plaf.synth.SynthScrollBarUI;
import java.io.*;
import java.util.*;

@Service(value = "CanalServerConfigService")
public class CanalServerConfigServiceImpl  implements CanalServerConfigService{

    @Autowired
    private CanalServerConfigMapper canalServerConfigMapper;//这里会报错，但是并不会影响

    @Override
    public void addCanalServer(CanalServerConfig canalServerConfig){
        canalServerConfigMapper.insert(canalServerConfig);
    }

    @Override
    public List<JSONObject> listCanalMenu(){
        List<String> canalServerIds = canalServerConfigMapper.selectCanalServerId();
        List<JSONObject> jsonObjects = new ArrayList<>();
        for(String canalServerId:canalServerIds){
            JSONObject jsonObject = returnJson(canalServerId);
            jsonObjects.add(jsonObject);
        }
        return jsonObjects;
    }

    public JSONObject returnJson(String canalServerId){
        List dataList = new ArrayList();
        List<Map<String,Object>> lists = canalServerConfigMapper.returnMenuJson(canalServerId);
        for (Map<String,Object> map:lists){
            HashMap dataRecord = new HashMap();
            for (String key:map.keySet()){
                dataRecord.put(key,map.get(key));
            }
            dataList.add(dataRecord);
        }
        HashMap nodeList = new HashMap();
        Node root = null;
        for (Iterator it = dataList.iterator(); it.hasNext();) {
            Map dataRecord = (Map) it.next();
            Node node = new Node();
            node.id = dataRecord.get("id").toString();
            node.text = (String) dataRecord.get("text");
            node.path = (String) dataRecord.get("path");
            if(dataRecord.get("parentId") == null){
                node.parentId = "";
            }else{
                node.parentId = dataRecord.get("parentId").toString();
            }
            nodeList.put(node.id, node);
        }
        Set entrySet = nodeList.entrySet();
        for (Iterator it = entrySet.iterator(); it.hasNext();) {
            Node node = (Node) ((Map.Entry) it.next()).getValue();
            if (node.parentId == null || node.parentId.equals("")) {
                root = node;
            } else {
                ((Node) nodeList.get(node.parentId)).addChild(node);
            }
        }
        JSONObject jsonObject = JSONObject.parseObject(root.toString());
        return jsonObject;

    }

    @Override
    public void deleteCanalServer(String canalServerName){
        canalServerConfigMapper.deleteCanalServerByCanalServerName(canalServerName);
    }

    @Override
    public void updateCanalServer(CanalServerConfig canalServerConfig){
        canalServerConfigMapper.updateCanalServerByCanalServerName(canalServerConfig);
    }

    @Override
    public Map<String,Object> selectCanalServerOldConfig(String canalServerName){
        return canalServerConfigMapper.selectCanalServerOldConfigByCanalServerName(canalServerName);
    }


    @Override
    public Map<String,Object>  startCanalServer(String canalServerName,String type) {
        CanalServerConfig deployInfo = canalServerConfigMapper.selectDeployInfo(canalServerName);
        String canalHome = deployInfo.getCanalHome();
        String canalServerHost = deployInfo.getCanalServerHost();
        String standbyServerHost = deployInfo.getStandbyServerHost();
        String startCanalCmd = "export PATH=$PATH:/usr/local/java/bin && /bin/bash " + canalHome + "/bin/startup.sh";
        Map<String,Object> resMap = new HashMap<>();
        if ("master".equals(type)){
            RmtShellExecutor exe = new RmtShellExecutor(canalServerHost, "hadoop", "hadoop",23245);
            try {
                resMap = exe.exec(startCanalCmd);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if ("standby".equals(type)){
            RmtShellExecutor exe = new RmtShellExecutor(standbyServerHost, "hadoop", "hadoop",23245);
            try {
                resMap = exe.exec(startCanalCmd);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resMap;

    }

    @Override
    public Map<String,Object>  stopCanalServer(String canalServerName,String type) {
        CanalServerConfig deployInfo = canalServerConfigMapper.selectDeployInfo(canalServerName);
        String canalHome = deployInfo.getCanalHome();
        String canalServerHost = deployInfo.getCanalServerHost();
        String standbyServerHost = deployInfo.getStandbyServerHost();
        String startCanalCmd = "/bin/bash " + canalHome + "/bin/stop.sh";
        Map<String,Object> resMap = new HashMap<>();
        if ("master".equals(type)){
            RmtShellExecutor exe = new RmtShellExecutor(canalServerHost, "hadoop", "hadoop",23245);
            try {
                resMap = exe.exec(startCanalCmd);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if ("standby".equals(type)){
            RmtShellExecutor exe = new RmtShellExecutor(standbyServerHost, "hadoop", "hadoop",23245);
            try {
                resMap = exe.exec(startCanalCmd);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resMap;

    }



    @Override
    public boolean deployCanalServer(String canalServerName) {
    	//从数据库中查出相关配置文件
        CanalServerConfig deployInfo = canalServerConfigMapper.selectDeployInfo(canalServerName);
        String canalHome = deployInfo.getCanalHome();
        String canalServerHost = deployInfo.getCanalServerHost();
        String canalServerConfiguration = deployInfo.getCanalServerConfiguration();
        String standbyServerHost = deployInfo.getStandbyServerHost();
        String standbyServerConfiguration = deployInfo.getStandbyServerConfiguration();
        
        //配置文件对应变量
        String fileName = "/tmp/"+canalServerName+"."+"canal.properties";
        String fileNameStandby = "/tmp/"+canalServerName+"."+"canal.standby.properties";
        String deployHome = canalHome+ "/conf/canal.properties";
        String deployHomeStandby = canalHome+ "/conf/canal.properties";
        
        //先在canalMate本地/tmp目录生成配置文件,再把该文件配置scp到canal服务器的conf目录下
        if (StringUtils.isNotBlank(standbyServerHost) && StringUtils.isNotBlank(standbyServerConfiguration)){
            if (ServiceUtil.scpFile(fileName,canalServerConfiguration,canalServerHost,deployHome) &&
                    ServiceUtil.scpFile(fileNameStandby,standbyServerConfiguration,standbyServerHost,deployHomeStandby) ){
                return true;
            }else{
                return false;
            }
        }else{
            return ServiceUtil.scpFile(fileName,canalServerConfiguration,canalServerHost,deployHome);
        }

    }





}
