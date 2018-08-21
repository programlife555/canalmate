package com.ppdai.canalmate.api.service.canal.server;

import java.io.IOException;
import java.util.Map;

import com.ppdai.canalmate.api.model.canal.server.ClientConfig;

public interface ClientConfigService {

    void addClient(ClientConfig clientConfig);

    void deleteClient(String clientName);

    Map<String,Object> selectClientOldConfig(String clientName);

    void updateClient(ClientConfig clientConfig);

    Map<String,Object> startClient(String clientName,String type) throws IOException;

    Map<String,Object> stopClient(String clientName,String type) throws IOException;

}
