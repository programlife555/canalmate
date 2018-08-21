package com.ppdai.canalmate.client.bean;

import java.io.Serializable;

public class Schema implements Serializable {

    private static final long serialVersionUID = 4L;
    String name;
    boolean isKey;
    boolean isNull;
    String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isKey() {
        return isKey;
    }

    public void setKey(boolean key) {
        isKey = key;
    }

    public boolean isNull() {
        return isNull;
    }

    public void setNull(boolean aNull) {
        isNull = aNull;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Schema{" +
                "name='" + name + '\'' +
                ", isKey=" + isKey +
                ", isNull=" + isNull +
                ", type='" + type + '\'' +
                '}';
    }
}
