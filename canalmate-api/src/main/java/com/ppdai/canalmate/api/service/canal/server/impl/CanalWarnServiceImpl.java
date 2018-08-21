package com.ppdai.canalmate.api.service.canal.server.impl;

import com.ppdai.canalmate.api.dao.canal.server.CanalWarnMapper;
import com.ppdai.canalmate.api.dao.canal.server.ClientConfigMapper;
import com.ppdai.canalmate.api.model.canal.server.CanalWarn;
import com.ppdai.canalmate.api.model.canal.server.ClientConfig;
import com.ppdai.canalmate.api.service.canal.server.CanalWarnService;
import com.ppdai.canalmate.api.service.canal.server.ClientConfigService;
import com.ppdai.canalmate.common.utils.RmtShellExecutor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service(value = "CanalWarnService")
public class CanalWarnServiceImpl implements CanalWarnService{

    @Autowired
    private CanalWarnMapper canalWarnMapper;

	@Override
	public void insertCanalWarn(CanalWarn canalWarn) {
		canalWarnMapper.insert(canalWarn);
	}

	@Override
	public void insertDefaultCanalWarn(CanalWarn canalWarn) {
		canalWarnMapper.insertDefault(canalWarn);
	}


}
