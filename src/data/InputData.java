package data;

import lombok.Getter;
import serves.LogTools;

import java.util.Map;

@Getter
public class InputData {
    private final String userName;
    private final String fileType;
    private int simpleData;
    private Map<String, Integer> dataMap;
    private final String dataType;
    private final String ip;

    public InputData(String userName, String fileType, int simpleData, String dataType, String ip) {
        this.userName = userName;
        this.fileType = fileType;
        this.simpleData = simpleData;
        this.dataType = dataType;
        this.ip = ip;
        LogTools.statusLog("Пакет данных собран.");
    }

    public InputData(String userName, String fileType, Map<String, Integer> dataMap, String dataType, String ip) {
        this.userName = userName;
        this.fileType = fileType;
        this.dataMap = dataMap;
        this.dataType = dataType;
        this.ip = ip;
        LogTools.statusLog("Пакет данных собран.");
    }
}
