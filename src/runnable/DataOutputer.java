package runnable;

import data.InputData;
import output.ConsoleFileOutput;
import output.DefaultFileOutput;
import output.FileOutputType;
import output.JsonFileOutput;
import source.SingletonServerConfig;
import storage.SingletonServerDataStorage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class DataOutputer implements Runnable {

    @Override
    public void run() {
        while (true) {
            while (true) {
                InputData inputData = SingletonServerDataStorage.SERVER_DATA_STORAGE.getInputDataFromStorage();
                if (inputData != null) {

                    FileOutputType fileOutputType = outputFactory(inputData);
                    fileOutputType.outputData(getDataInMap(inputData));
                }
            }
        }
    }

    private FileOutputType outputFactory(InputData inputData) {
        return switch (inputData.getFileType()) {
            case "CONSOLE" -> new ConsoleFileOutput();
            case "PLAIN" -> new DefaultFileOutput();
            case "JSON" -> new JsonFileOutput();
            default -> throw new IllegalArgumentException();
        };
    }

    private HashMap<String, String> getDataInMap(InputData inputData) {
        HashMap<String, String> dataMap = new HashMap<>();
        dataMap.put("data", getData(inputData));
        dataMap.put("ip", getIp());
        dataMap.put("date", getDate());
        return dataMap;
    }

    private String getData(InputData inputData) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(inputData.getUserName() + ". ");
        if (inputData.getDataType().equals("SIMPLE")) {
            stringBuilder.append("Выбранный температурный режим: " + inputData.getSimpleData() + " ");
            stringBuilder.append(" градуса. ");
        } else {
            stringBuilder.append("Выбранные температурные режимы: ");
            for (String key : inputData.getDataMap().keySet()) {
                Integer value = inputData.getDataMap().get(key);
                stringBuilder.append(key + " - " + value + " градуса. ");
            }
        }
        return stringBuilder.toString();
    }

    private String getIp() {
        return SingletonServerConfig.SERVER_CONFIG.getClientIp();
    }

    private String getDate() {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String dateFormat = localDateTime.format(dateTimeFormatter);
        return dateFormat;
    }

}
