package com.ppdai.canalmate.api.controller.canal.server;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.ppdai.canalmate.api.service.canal.server.CanalWarnService;

import io.swagger.annotations.Api;

@Api(value = "TbConfigController", description = "系统配置的接口类")
@RestController
public class TbConfigController {
    @Autowired
    private CanalWarnService canalWarnService;


}
