package output;

import data.InputData;
import serves.LogTools;
import source.ServerClConfig;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DefaultFileOutput implements FileOutputType {
    private InputData inputData;

    public DefaultFileOutput(InputData inputData) {
        this.inputData = inputData;
    }

    @Override
    public void outputData() {
        Path path = Path.of(ServerClConfig.TEXT_FILE_OUTPUT);
        if (!Files.exists(path)) {
            try {
                Files.createDirectory(path);
            } catch (IOException e) {
                LogTools.exceptionLog("Директория уже сущесвует.");
            }
        }
        try (FileWriter fileWriter = new FileWriter(String.valueOf(path), true)) {
            fileWriter.write(outputFormater(inputData));
        } catch (IOException e) {
            LogTools.exceptionLog("Невозможно записать данные в файл.");
        }
    }

    private String outputFormater(InputData InputData) {
        StringBuilder stringBuilder = new StringBuilder();
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String dateFormat = localDateTime.format(dateTimeFormatter);
        stringBuilder.append(dateFormat + " ");
        stringBuilder.append(ServerClConfig.CLIENT_IP + "\n");
        stringBuilder.append(InputData.getUserName() + "\n");
        if (InputData.getDataType().equals("SIMPLE")) {
            stringBuilder.append("Выбранный температурный режим - " + InputData.getSimpleData());
            stringBuilder.append(" градуса.\n");
        } else {
            stringBuilder.append("Выбранные температурные режимы.\n");
            for (String key : InputData.getDataMap().keySet()) {
                Integer value = InputData.getDataMap().get(key);
                stringBuilder.append(key + " - " + value + " градуса.\n");
            }
        }
        stringBuilder.append("--------------------------------------\n");
        return stringBuilder.toString();
    }
}
