package com.ppdai.canalmate.client;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.ppdai.canalmate.client.bean.DataInfo;
import com.ppdai.canalmate.client.bean.RowData;
import com.ppdai.canalmate.client.bean.Schema;

import java.util.ArrayList;
import java.util.List;

public class RowProcess {

    DataInfo dataInfo = new DataInfo();
    List<CanalEntry.Column> columns ;
    ArrayList<Schema> schemas = new ArrayList<>();
    ArrayList<RowData> rowDatas = new ArrayList<>();

    public RowProcess(List<CanalEntry.Column> columns) {
        this.columns = columns;
    }

    public DataInfo process(){
        for (CanalEntry.Column column : columns) {
            String colName = column.getName();
            String colValue = column.getValue();
            boolean isKey = column.getIsKey();
            boolean isNull = column.getIsNull();
            String mysqlType = column.getMysqlType();
            int sqlType = column.getSqlType();


            Schema schema = new Schema();
            schema.setName(colName);
            schema.setKey(isKey);
            schema.setNull(isNull);
            schema.setType(mysqlType);
            schemas.add(schema);

            RowData rowData = new RowData();
            rowData.setColName(colName);
            rowData.setColData(colValue);
            rowData.setColSqlType(sqlType);
            rowDatas.add(rowData);
        }

        dataInfo.setRowData(rowDatas);
        dataInfo.setSchemas(schemas);

        return dataInfo;
    }

}
