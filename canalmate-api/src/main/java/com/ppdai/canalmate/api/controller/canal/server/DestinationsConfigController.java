package com.ppdai.canalmate.api.controller.canal.server;


import com.ppdai.canalmate.api.model.canal.server.DestinationsConfig;
import com.ppdai.canalmate.api.service.canal.server.DestinationsConfigService;
import com.ppdai.canalmate.common.utils.ServiceUtil;

import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class DestinationsConfigController {

    @Autowired
    private DestinationsConfigService destinationsConfigService;

    @RequestMapping(value="/addDestination")
    public Map<String,Object> addDestination(@ModelAttribute DestinationsConfig destinationsConfig){
        Map<String,Object> map = ServiceUtil.returnSuccess();
        if (StringUtils.isNotBlank(destinationsConfig.getCanalServerName()) &&
                StringUtils.isNotBlank(destinationsConfig.getDescription()) &&
                StringUtils.isNotBlank(destinationsConfig.getDestinationConfiguration()) &&
                StringUtils.isNotBlank(destinationsConfig.getDestinationName())){
            try{
                destinationsConfigService.addDestination(destinationsConfig);
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

    @RequestMapping(value="/deleteDestination")
    public Map<String,Object> deleteDestination(String destinationName){
        Map<String,Object> map = ServiceUtil.returnSuccess();
        if (StringUtils.isNotBlank(destinationName)){
            try{
                destinationsConfigService.deleteDestination(destinationName);
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

    @RequestMapping(value="/updateDestination")
    public Map<String,Object> updateDestination(@ModelAttribute DestinationsConfig destinationsConfig){
        Map<String,Object> map = ServiceUtil.returnSuccess();
        if (StringUtils.isNotBlank(destinationsConfig.getDestinationNameUpdateKey()) &&
                StringUtils.isNotBlank(destinationsConfig.getDescription()) &&
                StringUtils.isNotBlank(destinationsConfig.getDestinationConfiguration()) &&
                StringUtils.isNotBlank(destinationsConfig.getDestinationName())){
            try{
                destinationsConfigService.updateDestination(destinationsConfig);
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

    @RequestMapping(value="/selectDestinationOldConfig")
    public Map<String,Object> selectDestinationOldConfig(String destinationName){
        Map<String,Object> map = ServiceUtil.returnSuccess();
        if (StringUtils.isNotBlank(destinationName)){
            try{
                map.put("data", destinationsConfigService.selectDestinationOldConfig(destinationName));
            }catch (Exception e){
                return ServiceUtil.returnError();
            }
        }
        else{
            return ServiceUtil.returnError();
        }
        return map;
    }

    @RequestMapping(value="/deployDestination")
    public Map<String,Object> deployDestination(String destinationName){
        Map<String,Object> map = ServiceUtil.returnSuccess();
        if (StringUtils.isNotBlank(destinationName)){
            try{
                if(destinationsConfigService.deployDestination(destinationName)){
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


}
