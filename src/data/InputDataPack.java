package data;

import serves.DataType;

import java.util.Map;

public class InputDataPack {
    private String USER_NAME;
    private DataType FILE_TYPE;
    private int simpleData;
    private Map<String, Integer> dataMap;
    private DataType DATA_TYPE;

    public String getUserName() {
        return USER_NAME;
    }

    public DataType getFileType() {
        return FILE_TYPE;
    }

    public int getSimpleData() {
        return simpleData;
    }

    public Map<String, Integer> getDataMap() {
        return dataMap;
    }

    public DataType getDataType() {
        return DATA_TYPE;
    }
}
