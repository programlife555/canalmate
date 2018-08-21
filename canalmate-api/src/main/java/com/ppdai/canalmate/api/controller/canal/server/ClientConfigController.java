package com.ppdai.canalmate.api.controller.canal.server;


import com.ppdai.canalmate.api.model.canal.server.ClientConfig;
import com.ppdai.canalmate.api.service.canal.server.ClientConfigService;
import com.ppdai.canalmate.common.utils.ServiceUtil;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class ClientConfigController {

    @Autowired
    private ClientConfigService clientConfigService;

    @RequestMapping(value="/addClient")
    public Map<String,Object> addClient(@ModelAttribute ClientConfig clientConfig){
        Map<String,Object> map = ServiceUtil.returnSuccess();
        if (StringUtils.isNotBlank(clientConfig.getDestinationName()) &&
                StringUtils.isNotBlank(clientConfig.getZkServers()) &&
                StringUtils.isNotBlank(clientConfig.getClientName())){
            try{
                clientConfigService.addClient(clientConfig);
                map.put("data", null);
            }catch (Exception e){
                return ServiceUtil.returnError();
            }
        }
        else{
            return ServiceUtil.returnError();
        }
        return map;
    }

    @RequestMapping(value="/deleteClient")
    public Map<String,Object> deleteClient(String clientName){
        Map<String,Object> map = ServiceUtil.returnSuccess();
        if (StringUtils.isNotBlank(clientName)){
            try{
                clientConfigService.deleteClient(clientName);
                map.put("data", null);
            }catch (Exception e){
                return ServiceUtil.returnError();
            }
        }
        else{
            return ServiceUtil.returnError();
        }
        return map;
    }

    @RequestMapping(value="/selectClientOldConfig")
    public Map<String,Object> selectClientOldConfig(String clientName){
        Map<String,Object> map = ServiceUtil.returnSuccess();
        if (StringUtils.isNotBlank(clientName)){
            try{
                map.put("data", clientConfigService.selectClientOldConfig(clientName));
            }catch (Exception e){
                return ServiceUtil.returnError();
            }
        }
        else{
            return ServiceUtil.returnError();
        }
        return map;
    }

    @RequestMapping(value="/updateClient")
    public Map<String,Object> updateClient(@ModelAttribute ClientConfig clientConfig){
        Map<String,Object> map = ServiceUtil.returnSuccess();
        if (StringUtils.isNotBlank(clientConfig.getClientNameUpdateKey()) &&
                StringUtils.isNotBlank(clientConfig.getZkServers()) &&
                StringUtils.isNotBlank(clientConfig.getClientName())){
            try{
                clientConfigService.updateClient(clientConfig);
                map.put("data", null);
            }catch (Exception e){
                return ServiceUtil.returnError();
            }
        }
        else{
            return ServiceUtil.returnError();
        }
        return map;
    }


    @RequestMapping(value="/startClient")
    public Map<String,Object> startClient(String clientName,String type){
        Map<String,Object> map = ServiceUtil.returnSuccess();
        if (StringUtils.isNotBlank(clientName) && StringUtils.isNotBlank(type)){
            try{
                Map<String,Object> resultMap;
                resultMap = clientConfigService.startClient(clientName,type);
                String outStr = (String) resultMap.get("outStr");
                String outErr = (String) resultMap.get("outErr");
                int ret = (int) resultMap.get("ret");
                if( ret==0){
                    map.put("data",null);
                }else if( ret!=0 && outStr.contains("found")){
                    return ServiceUtil.returnError("后台已有canal client进程运行！");
                }else{
                    return ServiceUtil.returnError("启动异常，请检查启动脚本！");
                }
            }catch (Exception e){
                return ServiceUtil.returnError("其他异常！");
            }
        } else{
            return ServiceUtil.returnError("参数缺失或者传参有误！");
        }
        return map;
    }

    @RequestMapping(value="/stopClient")
    public Map<String,Object> stopClient(String clientName,String type){
        Map<String,Object> map = ServiceUtil.returnSuccess();
        if (StringUtils.isNotBlank(clientName) && StringUtils.isNotBlank(type)){
            try{
                Map<String,Object> resultMap;
                resultMap = clientConfigService.stopClient(clientName,type);
                String outStr = (String) resultMap.get("outStr");
                String outErr = (String) resultMap.get("outErr");
                int ret = (int) resultMap.get("ret");
                if( ret==0 && outStr.contains("is not running")){
                    return ServiceUtil.returnError("canal client后台不在运行！");
                }else if( ret==0){
                    map.put("data",null);
                }else{
                    return ServiceUtil.returnError("关闭异常，请登陆服务器检查！");
                }
            }catch (Exception e){
                return ServiceUtil.returnError("其他异常！");
            }
        } else{
            return ServiceUtil.returnError("参数缺失或者传参有误！");
        }
        return map;
    }





}
