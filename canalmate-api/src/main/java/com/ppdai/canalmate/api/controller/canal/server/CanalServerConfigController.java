package com.ppdai.canalmate.api.controller.canal.server;


import com.alibaba.fastjson.JSONObject;
import com.ppdai.canalmate.api.core.Result;
import com.ppdai.canalmate.api.model.canal.server.CanalServerConfig;
import com.ppdai.canalmate.api.service.canal.server.CanalServerConfigService;
import com.ppdai.canalmate.common.utils.ServiceUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "CanalServerConfigController", description = "CanalServerConfigController的接口类")
@RestController
public class CanalServerConfigController {
    @Autowired
    private CanalServerConfigService canalServerConfigService;

    @ApiOperation(value = "新增canal server")
    @RequestMapping(value="/addCanalServer")
    public Map<String,Object> addCanalServer(@ModelAttribute CanalServerConfig canalServerConfig){
        Map<String,Object> map = ServiceUtil.returnSuccess();
        if (StringUtils.isNotBlank(canalServerConfig.getCanalHome()) &&
                StringUtils.isNotBlank(canalServerConfig.getCanalServerType()) &&
                StringUtils.isNotBlank(canalServerConfig.getCanalServerConfiguration()) &&
                StringUtils.isNotBlank(canalServerConfig.getCanalServerPort()) &&
                StringUtils.isNotBlank(canalServerConfig.getCanalServerHost()) &&
                StringUtils.isNotBlank(canalServerConfig.getCanalServerName()) ){
            try{
                canalServerConfigService.addCanalServer(canalServerConfig);
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

    @ApiOperation(value = "返回Canal配置菜单")
    @RequestMapping(value="/listCanalMenu")
    public Map<String,Object> listCanalMenu(){
        Map<String,Object> map = ServiceUtil.returnSuccess();
        try{
            List<JSONObject> jsonObject = canalServerConfigService.listCanalMenu();
            map.put("data", jsonObject);
        }catch (Exception e){
            e.printStackTrace();
            return ServiceUtil.returnError();
        }
        return map;
    }

    @ApiOperation(value = "删除指定canal server")
    @RequestMapping(value="/deleteCanalServer")
    public Map<String,Object> deleteCanalServer(String canalServerName){
        Map<String,Object> map = ServiceUtil.returnSuccess();
        if (StringUtils.isNotBlank(canalServerName)){
            try{
                canalServerConfigService.deleteCanalServer(canalServerName);
                map.put("data", null);
            }catch (Exception e){
                return ServiceUtil.returnError();
            }
        } else{
            return ServiceUtil.returnError();
        }
        return map;
    }

    @ApiOperation(value = "更新指定的canal server")
    @RequestMapping(value="/updateCanalServer")
    public Map<String,Object> updateCanalServer(@ModelAttribute CanalServerConfig canalServerConfig){
        Map<String,Object> map = ServiceUtil.returnSuccess();
        if (StringUtils.isNotBlank(canalServerConfig.getCanalHome()) &&
                StringUtils.isNotBlank(canalServerConfig.getServerName()) &&
                StringUtils.isNotBlank(canalServerConfig.getCanalServerConfiguration()) &&
                StringUtils.isNotBlank(canalServerConfig.getCanalServerPort()) &&
                StringUtils.isNotBlank(canalServerConfig.getCanalServerHost()) &&
                StringUtils.isNotBlank(canalServerConfig.getCanalServerName()) ){
            try{
                canalServerConfigService.updateCanalServer(canalServerConfig);
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



    @ApiOperation(value = "查询老的canal server配置")
    @RequestMapping(value="/selectCanalServerOldConfig")
    public Map<String,Object> selectCanalServerOldConfig(String canalServerName){
        Map<String,Object> map = ServiceUtil.returnSuccess();
        if (StringUtils.isNotBlank(canalServerName)){
            try{
                map.put("data",  canalServerConfigService.selectCanalServerOldConfig(canalServerName));
            }catch (Exception e){
                return ServiceUtil.returnError();
            }
        } else{
            return ServiceUtil.returnError();
        }
        return map;
    }

    @RequestMapping(value="/deployCanalServer")
    public Map<String,Object> deployCanalServer(String canalServerName){
        Map<String,Object> map = ServiceUtil.returnSuccess();
        if (StringUtils.isNotBlank(canalServerName)){
            try{
                if(canalServerConfigService.deployCanalServer(canalServerName)){
                    map.put("data", null );
                }else{
                    return ServiceUtil.returnError();
                }

            }catch (Exception e){
                return ServiceUtil.returnError();
            }
        } else{
            return ServiceUtil.returnError();
        }
        return map;

    }

    @RequestMapping(value="/startCanalServer")
    public Map<String,Object> startCanalServer(String canalServerName,String type){
        Map<String,Object> map = ServiceUtil.returnSuccess();
        if (StringUtils.isNotBlank(canalServerName) && StringUtils.isNotBlank(type)){
            try{
                Map<String,Object> resultMap;
                resultMap = canalServerConfigService.startCanalServer(canalServerName,type);
                String outStr = (String) resultMap.get("outStr");
                String outErr = (String) resultMap.get("outErr");
                int ret = (int) resultMap.get("ret");
                if( ret==0){
                    map.put("data",null);
                }else if( ret!=0 && outStr.contains("found canal.pid")){
                    return ServiceUtil.returnError("后台已有canal server进程运行！");
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

    @RequestMapping(value="/stopCanalServer")
    public Map<String,Object> stopCanalServer(String canalServerName,String type){
        Map<String,Object> map = ServiceUtil.returnSuccess();
        if (StringUtils.isNotBlank(canalServerName) && StringUtils.isNotBlank(type)){
            try{
                Map<String,Object> resultMap;
                resultMap = canalServerConfigService.stopCanalServer(canalServerName,type);
                String outStr = (String) resultMap.get("outStr");
                String outErr = (String) resultMap.get("outErr");
                int ret = (int) resultMap.get("ret");
                if( ret==0 && outStr.contains("canal is not running")){
                    return ServiceUtil.returnError("canal server后台不在运行！");
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
