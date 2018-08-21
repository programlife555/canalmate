package com.ppdai.canalmate.api.dao.canal.server;

import javafx.beans.binding.ObjectExpression;
import org.apache.ibatis.annotations.Mapper;

import com.ppdai.canalmate.api.model.canal.server.DestinationsConfig;

import java.util.Map;

@Mapper
public interface DestinationsConfigMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DestinationsConfig record);

    int insertSelective(DestinationsConfig record);

    DestinationsConfig selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DestinationsConfig record);

    int updateByPrimaryKey(DestinationsConfig record);

    Long selectCanalId(String canalServerName);

    void deleteDestinationByDestinationName(String destinationName);

    Map<String,Object> selectDestinationOldConfigByDestinationName(String destinationName);

    void updateDestinationByDestinationName(DestinationsConfig record);

    Map<String,Object> selectDestinationDeployInfo(String destinationName);

}