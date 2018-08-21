package com.ppdai.canalmate.api.dao.canal.server;

import org.apache.ibatis.annotations.Mapper;

import com.ppdai.canalmate.api.model.canal.server.ClientConfig;

import java.util.Map;

@Mapper
public interface ClientConfigMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ClientConfig record);

    int insertSelective(ClientConfig record);

    ClientConfig selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ClientConfig record);

    Long selectDestinationId(String destinationName);

    int updateByPrimaryKey(ClientConfig record);

    void addClient(ClientConfig clientConfig);

    void deleteByClientName(String clientName);

    Map<String,Object> selectClientOldConfig(String clientName);

    Map<String,Object> selectClientConfig(String clientName);

    void updateByClientName(ClientConfig clientConfig);
}