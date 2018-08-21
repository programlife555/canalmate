package com.ppdai.canalmate.api.dao.canal.server;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ppdai.canalmate.api.model.canal.server.TbCanalinstanceConfig;

@Mapper
public interface TbCanalinstanceConfigMapper {
	
	int deleteByPrimaryKey(Integer pkId);

	int insert(TbCanalinstanceConfig record);

	int insertSelective(TbCanalinstanceConfig record);

	TbCanalinstanceConfig selectByPrimaryKey(Integer pkId);

	int updateByPrimaryKeySelective(TbCanalinstanceConfig record);

	int updateByPrimaryKey(TbCanalinstanceConfig record);

	//add by chh
    List<TbCanalinstanceConfig> selectByExample(TbCanalinstanceConfig record);

}