package com.ppdai.canalmate.api.service.canal.server;

import org.springframework.stereotype.Component;

import com.ppdai.canalmate.api.model.canal.server.CanalWarn;
import com.ppdai.canalmate.api.model.canal.server.TbConfig;
@Component
public interface TbConfigService {

    int insert(TbConfig tbConfig);

    TbConfig selectByConfigName(String configName);
}
