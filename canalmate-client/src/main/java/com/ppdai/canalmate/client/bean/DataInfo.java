package com.ppdai.canalmate.client.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class DataInfo implements Serializable {

    private static final long serialVersionUID = 1L;


    ArrayList<RowData> rowData;
    ArrayList<Schema> schemas;

    public ArrayList<Schema> getSchemas() {
        return schemas;
    }

    public void setSchemas(ArrayList<Schema> schemas) {
        this.schemas = schemas;
    }

    public ArrayList<RowData> getRowData() {
        return rowData;
    }

    public void setRowData(ArrayList<RowData> rowData) {
        this.rowData = rowData;
    }

    @Override
    public String toString() {
        return "DataInfo{" +
                "rowData=" + rowData +
                ", schemas=" + schemas +
                '}';
    }
}
