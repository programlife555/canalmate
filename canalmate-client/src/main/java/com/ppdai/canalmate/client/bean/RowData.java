package com.ppdai.canalmate.client.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class RowData implements Serializable {

    private static final long serialVersionUID = 3L;
    public String colName;

    public String colData;

    public int colSqlType;


    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public String getColData() {
        return colData;
    }

    public void setColData(String colData) {
        this.colData = colData;
    }

    public int getColSqlType() {
        return colSqlType;
    }

    public void setColSqlType(int colType) {
        this.colSqlType = colType;
    }

    @Override
    public String toString() {
        return "RowData{" +
                "colName='" + colName + '\'' +
                ", colData='" + colData + '\'' +
                ", colType='" + colSqlType + '\'' +
                '}';
    }
}
