package com.ppdai.canalmate.api.service.canal.server;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ppdai.canalmate.api.model.canal.server.CanalServerConfig;
import com.ppdai.canalmate.api.model.canal.server.CanalWarn;
import com.ppdai.canalmate.api.model.canal.server.ClientConfig;
@Component
public interface CanalWarnService {

    void insertCanalWarn(CanalWarn canalWarn);

    void insertDefaultCanalWarn(CanalWarn canalWarn);
}
