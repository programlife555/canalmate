package com.ppdai.canalmate.client;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.google.gson.Gson;
import com.ppdai.canalmate.client.bean.DataInfo;
import com.ppdai.canalmate.client.bean.EntryInfo;
import com.ppdai.canalmate.client.bean.Schema;
import com.ppdai.canalmate.client.producer.Sender;

import java.util.ArrayList;
import java.util.List;

public class EntryProcess {

    List<CanalEntry.Entry> entrys;
    Sender sender;

    public EntryProcess(List<CanalEntry.Entry> entrys, Sender sender){
        this.entrys = entrys;
        this.sender = sender;
    }

    public void process(){
        for (CanalEntry.Entry entry : entrys) {
            if (entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONBEGIN || entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONEND) {
                continue;
            }
            CanalEntry.RowChange rowChage = null;
            try {
                rowChage = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
            } catch (Exception e) {
                throw new RuntimeException("ERROR ## parser of eromanga-event has an error , data:" + entry.toString(),
                        e);
            }
            CanalEntry.EventType eventType = rowChage.getEventType();
            String logFileName = entry.getHeader().getLogfileName();
            Long logFileOffset = entry.getHeader().getLogfileOffset();
            String dbName = entry.getHeader().getSchemaName();
            String tableName = entry.getHeader().getTableName();

            for (CanalEntry.RowData rowData : rowChage.getRowDatasList()) {
                EntryInfo entryInfo = new EntryInfo();
                DataInfo dataInfo;
                if (eventType == CanalEntry.EventType.DELETE) {
                    break;
                } else if (eventType == CanalEntry.EventType.INSERT) {
                    RowProcess rowProcess = new RowProcess(rowData.getAfterColumnsList());
                    dataInfo = rowProcess.process();
                } else {
                    RowProcess rowProcess = new RowProcess(rowData.getAfterColumnsList());
                    dataInfo = rowProcess.process();
                }
                
                //发送给kafka时，按主键分区
                ArrayList<Schema> schemas = dataInfo.getSchemas();
                StringBuilder stringBuilder=new StringBuilder();
                for(Schema schema:schemas){
                    if(schema.isKey()){
                        stringBuilder.append(schema.getName());
                    }
                }
                entryInfo.setDataInfo(dataInfo);
                entryInfo.setDbName(dbName);
                entryInfo.setEventType(eventType);
                entryInfo.setLogFileName(logFileName);
                entryInfo.setLogFileOffset(logFileOffset);
                entryInfo.setTableName(tableName);
                Gson gson = new Gson();
                String jsonStr = gson.toJson(entryInfo);
                sender.send(dbName+"_"+tableName,stringBuilder.toString(),jsonStr);

            }
        }

    }
}

