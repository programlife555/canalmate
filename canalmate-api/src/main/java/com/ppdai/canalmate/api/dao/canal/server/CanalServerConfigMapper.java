package com.ppdai.canalmate.api.dao.canal.server;

import org.apache.ibatis.annotations.Mapper;

import com.ppdai.canalmate.api.model.canal.server.CanalServerConfig;

import java.util.List;
import java.util.Map;

@Mapper
public interface CanalServerConfigMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CanalServerConfig record);

    int insertSelective(CanalServerConfig record);

    CanalServerConfig selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CanalServerConfig record);

    int updateByPrimaryKey(CanalServerConfig record);

    void deleteCanalServerByCanalServerName(String canalServerName);

    void updateCanalServerByCanalServerName(CanalServerConfig record);

    List<String> selectCanalServerId();

    List<Map<String,Object>> returnMenuJson(String canalServerId);

    Map<String,Object> selectCanalServerOldConfigByCanalServerName(String canalServerName);

    CanalServerConfig selectDeployInfo(String canalServerName);

}