package com.ppdai.canalmate.client.serialization;

import org.apache.kafka.common.errors.SerializationException;

import com.ppdai.canalmate.client.bean.EntryInfo;

import java.io.*;

public class SerializeUtil {

    public static byte[] serialize(EntryInfo entryInfo) {
        ByteArrayOutputStream baos = null;
        ObjectOutputStream oos  = null;
        try {
            // serialize
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(entryInfo);
            byte[] bytes = baos.toByteArray();
            return bytes;
        } catch (IOException | RuntimeException e) {
            throw new SerializationException("Error serializing value", e);
        }
    }
    public static EntryInfo deserialize(byte[] bytes) {
        ByteArrayInputStream bais = null;
        try {
            // deserialize
            bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (EntryInfo)ois.readObject();
        } catch (Exception e) {
            throw new SerializationException("Error deserializing value", e);
        }
    }

}
