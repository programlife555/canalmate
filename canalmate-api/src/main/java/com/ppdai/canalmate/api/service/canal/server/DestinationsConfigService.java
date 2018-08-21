package com.ppdai.canalmate.api.service.canal.server;

import java.io.IOException;
import java.util.Map;

import com.ppdai.canalmate.api.model.canal.server.DestinationsConfig;

public interface DestinationsConfigService {

    void addDestination(DestinationsConfig destinationsConfig);

    void deleteDestination(String destinationName);

    Map<String,Object> selectDestinationOldConfig(String destinationName);

    void updateDestination(DestinationsConfig destinationsConfig);

    boolean deployDestination(String destinationName) throws IOException;



}
