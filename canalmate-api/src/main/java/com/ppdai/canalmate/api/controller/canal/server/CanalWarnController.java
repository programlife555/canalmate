package com.ppdai.canalmate.api.controller.canal.server;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.ppdai.canalmate.api.service.canal.server.CanalWarnService;

import io.swagger.annotations.Api;

@Api(value = "CanalWarnController", description = "CanalWarnController的接口类")
@RestController
public class CanalWarnController {
    @Autowired
    private CanalWarnService canalWarnService;


}
