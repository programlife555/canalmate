package com.ppdai.canalmate.api.dao.canal.server;

import org.apache.ibatis.annotations.Mapper;

import com.ppdai.canalmate.api.model.canal.server.CanalWarn;

@Mapper
public interface CanalWarnMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CanalWarn record);
    
    //新加的方法，不对isactive做insert
    int insertDefault(CanalWarn record);

    int insertSelective(CanalWarn record);

    CanalWarn selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CanalWarn record);

    int updateByPrimaryKey(CanalWarn record);
}