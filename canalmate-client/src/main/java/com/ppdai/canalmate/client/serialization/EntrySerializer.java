package com.ppdai.canalmate.client.serialization;

import org.apache.kafka.common.serialization.Serializer;

import com.ppdai.canalmate.client.bean.EntryInfo;

import java.util.Map;

public class EntrySerializer implements Serializer<EntryInfo> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // nothing to do
    }

    @Override
    public byte[] serialize(String topic, EntryInfo data) {
        if (data == null) {
            return null;
        }
        byte[] bytes = SerializeUtil.serialize(data);
        return bytes;
    }

    @Override
    public void close() {

    }

}
