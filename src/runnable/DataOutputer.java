package runnable;

import dataclasses.InputData;
import dataenums.DayTimeSettings;
import output.*;
import serves.ClientData;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static source.SingletonServerConfig.SERVER_CONFIG;
import static storage.SingletonServerDataStorage.SERVER_DATA_STORAGE;

public class DataOutputer implements Runnable {


    @Override
    public void run() {
        while (true) {
            InputData inputData = SERVER_DATA_STORAGE.getInputDataFromStorage();
            if (inputData != null) {
                FileOutputType fileOutputType = outputFactory(inputData);
                fileOutputType.outputData(getClientData(inputData));
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

    private ClientData getClientData(InputData inputData) {
        return new ClientData(getDate(), getIp(), getData(inputData));
    }

    private String[] getData(InputData inputData) {
        String[] data = new String[inputData.getDataMap().size() + 2];
        data[0] = inputData.getUserName();
        data[1] = "Выбранные температурные режим:";
        int x = 2;
        for (DayTimeSettings key : inputData.getDataMap().keySet()) {
            Integer value = inputData.getDataMap().get(key);
            data[x++] = (key.getValue()) + (" - ") + (value) + (" град. ");
        }
        return data;
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
