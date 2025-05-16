package output;

import data.InputData;
import serves.LogTools;
import source.ServerClConfig;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JsonFileOutput implements FileOutputType {
    InputData inputData;

    public JsonFileOutput(InputData inputData) {
        this.inputData = inputData;
    }

    @Override
    public void outputData() {
        Path path = Path.of(ServerClConfig.JSON_FILE_OUTPUT);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                LogTools.exceptionLog("Директория уже сущесвует.");
            }
        }
        try (FileWriter fileWriter = new FileWriter(String.valueOf(path))) {
            fileWriter.write(toJson());
        } catch (IOException e) {
            LogTools.exceptionLog("Не возможно записать файл.");
        }
    }

    private String toJson() {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String dateFormat = localDateTime.format(dateTimeFormatter);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[\n");
        stringBuilder.append("{\n");
        stringBuilder.append("\"date\":\"" +
                dateFormat +
                "\",\n");
        stringBuilder.append("\n");
        stringBuilder.append("\"ip\":\"" +
                ServerClConfig.CLIENT_IP +
                "\",\n");
        stringBuilder.append("\n");
        if (inputData.getDataType().equals("SIMPLE")) {
            stringBuilder.append("\"data\":\"" +
                    "Выбранный температурный режим " +
                    inputData.getSimpleData() +
                    " град." +
                    "\"\n");
        } else {
            StringBuilder stringBuilder2 = new StringBuilder();
            for (String key : inputData.getDataMap().keySet()) {
                Integer value = inputData.getDataMap().get(key);
                stringBuilder2.append(key + " - " + value + " град.");
            }
            stringBuilder.append("\"data\":\"" +
                    "Выбранный температурный режим " +
                    stringBuilder2 +
                    "град." +
                    "\"\n");
        }
        stringBuilder.append("}\n");
        stringBuilder.append("]\n");
        return stringBuilder.toString();
    }

}
