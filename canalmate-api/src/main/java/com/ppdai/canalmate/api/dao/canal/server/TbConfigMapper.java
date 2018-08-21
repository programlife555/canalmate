package com.ppdai.canalmate.api.dao.canal.server;

import org.apache.ibatis.annotations.Mapper;

import com.ppdai.canalmate.api.model.canal.server.TbConfig;

@Mapper
public interface TbConfigMapper {
    int deleteByPrimaryKey(Integer pkConfigId);

    int insert(TbConfig record);

    int insertSelective(TbConfig record);

    TbConfig selectByPrimaryKey(Integer pkConfigId);
    
    TbConfig selectByConfigName(String configName);

    int updateByPrimaryKeySelective(TbConfig record);

    int updateByPrimaryKey(TbConfig record);
}