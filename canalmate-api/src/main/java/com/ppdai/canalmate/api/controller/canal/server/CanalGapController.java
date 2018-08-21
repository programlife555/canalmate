package com.ppdai.canalmate.api.controller.canal.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.otter.canal.protocol.position.LogPosition;
import com.ppdai.canalmate.api.core.BaseController;
import com.ppdai.canalmate.api.core.Result;
import com.ppdai.canalmate.api.core.ResultCode;
import com.ppdai.canalmate.api.dataaccess.auth.UserDataAccess;
import com.ppdai.canalmate.api.entity.dto.MenuDto;
import com.ppdai.canalmate.api.entity.dto.ResultResponse;
import com.ppdai.canalmate.api.entity.dto.canal.CanalGapDto;
import com.ppdai.canalmate.api.entity.dto.canal.CanalInstanceConfigDto;
import com.ppdai.canalmate.api.model.canal.server.CanalServerConfig;
import com.ppdai.canalmate.api.model.canal.server.DestinationsConfig;
import com.ppdai.canalmate.api.model.canal.server.TbCanalinstanceConfig;
import com.ppdai.canalmate.api.service.canal.CanalGapService;
import com.ppdai.canalmate.api.service.canal.CanalInstanceService;
import com.ppdai.canalmate.api.service.canal.ProcessMonitorService;
import com.ppdai.canalmate.common.cons.CanalConstants;
import com.ppdai.canalmate.common.model.ZKDestinationBean;
import com.ppdai.canalmate.common.utils.CanalPropertyUtils;
import com.ppdai.canalmate.common.utils.CanalZKUtils;
import com.ppdai.canalmate.common.utils.DBUtils;
import com.ppdai.canalmate.common.utils.DateUtil;
import com.ppdai.canalmate.common.utils.P;
import com.ppdai.canalmate.common.utils.ReponseEnum;
import com.ppdai.canalmate.common.utils.StrUtil;
import com.ppdai.canalmate.common.utils.ZKClient;
import com.ppdai.canalmate.common.utils.ZookeeperPathUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 用户Controller
 */
@Api(value = "CanalGapController", description = "延迟监控的接口类")
@RestController
@EnableAutoConfiguration
public class CanalGapController extends BaseController{

    @Qualifier(value = "canalGapService")
    @Autowired
    CanalGapService canalGapService;
    
    @Qualifier(value = "processMonitorService")
    @Autowired
    ProcessMonitorService processMonitorService;

    Logger logger = LoggerFactory.getLogger(CanalGapController.class);

    @ApiOperation(value = "列出canal instance的延迟",httpMethod ="GET", response = Result.class)
    @RequestMapping(value = "/canalgap/list", method = RequestMethod.GET)
    public Result listInstanceStatus(@ApiParam(required = true, value = "destinations_config的canal_id，关联canal_server_config主键") @RequestParam(value = "canal_id", required=true) String canalId) {
        
    	List<CanalGapDto> instanceGapList =canalGapService.listInstanceStatus(canalId);

		 Result result = new Result();
	     result.setCode(ReponseEnum.SUCCEED.getResCode());
	     result.setMessage(ReponseEnum.SUCCEED.getResMsg());
	     result.setData(instanceGapList);
	     return result;
    }
    

}
