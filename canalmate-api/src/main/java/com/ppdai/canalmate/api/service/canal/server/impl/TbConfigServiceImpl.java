package com.ppdai.canalmate.api.service.canal.server.impl;

import com.ppdai.canalmate.api.dao.canal.server.CanalWarnMapper;
import com.ppdai.canalmate.api.dao.canal.server.ClientConfigMapper;
import com.ppdai.canalmate.api.dao.canal.server.TbConfigMapper;
import com.ppdai.canalmate.api.model.canal.server.CanalWarn;
import com.ppdai.canalmate.api.model.canal.server.ClientConfig;
import com.ppdai.canalmate.api.model.canal.server.TbConfig;
import com.ppdai.canalmate.api.service.canal.server.CanalWarnService;
import com.ppdai.canalmate.api.service.canal.server.ClientConfigService;
import com.ppdai.canalmate.api.service.canal.server.TbConfigService;
import com.ppdai.canalmate.common.utils.RmtShellExecutor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service(value = "TbConfigService")
public class TbConfigServiceImpl implements TbConfigService{

    @Autowired
    private TbConfigMapper tbConfigMapper;

	@Override
	public int insert(TbConfig tbConfig) {
		return tbConfigMapper.insert(tbConfig);
	}

	@Override
	public TbConfig selectByConfigName(String configName) {
		return tbConfigMapper.selectByConfigName(configName);
	}


}
