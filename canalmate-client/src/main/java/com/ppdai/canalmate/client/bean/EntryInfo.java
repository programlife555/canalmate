package com.ppdai.canalmate.client.bean;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.ppdai.canalmate.client.bean.DataInfo;

import java.io.Serializable;

public class EntryInfo implements Serializable {

    private static final long serialVersionUID = 2L;
    CanalEntry.EventType eventType;
    String logFileName;
    Long logFileOffset;
    String dbName;
    String tableName;
    DataInfo dataInfo;


    public DataInfo getDataInfo() {
        return dataInfo;
    }

    public void setDataInfo(DataInfo dataInfo) {
        this.dataInfo = dataInfo;
    }


    public CanalEntry.EventType getEventType() {
        return eventType;
    }

    public void setEventType(CanalEntry.EventType eventType) {
        this.eventType = eventType;
    }

    public String getLogFileName() {
        return logFileName;
    }

    public void setLogFileName(String logFileName) {
        this.logFileName = logFileName;
    }

    public Long getLogFileOffset() {
        return logFileOffset;
    }

    public void setLogFileOffset(Long logFileOffset) {
        this.logFileOffset = logFileOffset;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public String toString() {
        return "{" +
                "\"eventType\":" + "\"" +eventType + "\"" +
                ",\"logFileName\":" + "\"" + logFileName + "\"" +
                ",\"logFileOffset\":" + logFileOffset +
                ",\"dbName\":" + "\"" + dbName + "\"" +
                ",\"tableName\":" +  "\"" + tableName + "\"" +
                "," + dataInfo +
                '}';
    }
}
