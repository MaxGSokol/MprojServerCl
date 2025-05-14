package runnable;

import data.InputData;
import output.ConsoleFileOutput;
import output.DefaultFileOutput;
import output.FileOutputType;
import output.JsonFileOutput;
import storage.DataStorage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DataOutputer implements Runnable {

    @Override
    public void run() {

        while (true) {
            InputData inputData = DataStorage.INPUT_DATA_STORAGE.pollLast();
            if (inputData == null) {
                continue;
            }
            FileOutputType fileOutputType = outputFactory(inputData);
            fileOutputType.outputData(outputFormater(inputData));
        }
    }

    private String outputFormater(InputData inputData) {
        StringBuilder stringBuilder = new StringBuilder();
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String dateFormat = localDateTime.format(dateTimeFormatter);
        stringBuilder.append(dateFormat + " ");
        stringBuilder.append(inputData.getIp() + "\n");
        stringBuilder.append(inputData.getUserName() + "\n");
        if (inputData.getDataType().equals("SIMPLE")) {
            stringBuilder.append("Выбранный температурный режим - " + inputData.getSimpleData());
            stringBuilder.append(" градуса.\n");
        } else {
            stringBuilder.append("Выбранные температурные режимы.\n");
            for (String key : inputData.getDataMap().keySet()) {
                Integer value = inputData.getDataMap().get(key);
                stringBuilder.append(key + " - " + value + " градуса.\n");
            }
        }
        stringBuilder.append("--------------------------------------\n");
        return stringBuilder.toString();
    }

    private FileOutputType outputFactory(InputData inputData) {
        FileOutputType fileOutputType = null;
        switch (inputData.getFileType()) {
            case "CONSOLE":
                fileOutputType = new ConsoleFileOutput();
                break;
            case "PLAIN":
                fileOutputType = new DefaultFileOutput();
                break;
            case "JSON":
                fileOutputType = new JsonFileOutput();
                break;
        }
        return fileOutputType;
    }

}
