package com.ppdai.canalmate.client.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Sender {

    private static final Logger LOG = LoggerFactory.getLogger(Sender.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void send(String topic,String key,String entryInfo){
        LOG.info("sending message='{}' to topic='{}'", entryInfo, topic);
        kafkaTemplate.send(topic, key, entryInfo);
    }
}