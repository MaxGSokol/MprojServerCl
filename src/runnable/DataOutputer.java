package runnable;

import dataclasses.InputData;
import dataenums.DayTimeSettings;
import output.ConsoleFileOutput;
import output.DefaultFileOutput;
import output.FileOutputType;
import output.JsonFileOutput;
import serves.OutputDataMarks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TreeMap;

import static source.SingletonServerConfig.SERVER_CONFIG;
import static storage.SingletonServerDataStorage.SERVER_DATA_STORAGE;

public class DataOutputer implements Runnable {


    @Override
    public void run() {
        while (true) {
            InputData inputData = SERVER_DATA_STORAGE.getInputDataFromStorage();
            if (inputData != null) {

                FileOutputType fileOutputType = outputFactory(inputData);
                fileOutputType.outputData(getDataInMap(inputData));
            }
        }
    }

    private FileOutputType outputFactory(InputData inputData) {
        return switch (inputData.getFileType()) {
            case CONSOLE -> new ConsoleFileOutput();
            case PLAIN -> new DefaultFileOutput();
            case JSON -> new JsonFileOutput();
        };
    }

    private TreeMap<OutputDataMarks, String> getDataInMap(InputData inputData) {
        TreeMap<OutputDataMarks, String> dataMap = new TreeMap<>();
        dataMap.put(OutputDataMarks.DATA, getData(inputData));
        dataMap.put(OutputDataMarks.IP, getIp());
        dataMap.put(OutputDataMarks.DATE, getDate());
        return dataMap;
    }

    private String getData(InputData inputData) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(inputData.getUserName()).append(". ")
                .append("Выбранные температурные режим: ");
        for (DayTimeSettings key : inputData.getDataMap().keySet()) {
            Integer value = inputData.getDataMap().get(key);
            stringBuilder.append(key.getValue()).append(" - ").append(value).append(" град. ");
        }
        return stringBuilder.toString();
    }

    private String getIp() {
        return SERVER_CONFIG.getClientIp().replaceAll("/", "");
    }

    private String getDate() {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return localDateTime.format(dateTimeFormatter);
    }

}
