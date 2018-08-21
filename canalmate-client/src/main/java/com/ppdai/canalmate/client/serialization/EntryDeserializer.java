package com.ppdai.canalmate.client.serialization;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;

import com.ppdai.canalmate.client.bean.EntryInfo;

import java.util.Map;

public class EntryDeserializer implements Deserializer<EntryInfo> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // nothing to do
    }

    @Override
    public EntryInfo deserialize(String topic, byte[] data) {
        if (data == null) {
            return null;
        }
        EntryInfo entryInfo = SerializeUtil.deserialize(data);
        return entryInfo;
    }

    @Override
    public void close() {

    }
}
