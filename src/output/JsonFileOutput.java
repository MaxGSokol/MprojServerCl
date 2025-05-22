package output;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import serves.LogTools;
import serves.OneLevelJsonParser;
import serves.OutputDataMarks;
import source.SingletonServerConfig;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.TreeMap;

public class JsonFileOutput implements FileOutputType {

    @Override
    public void outputData(TreeMap<OutputDataMarks, String> map) {
        Path path = Path.of(SingletonServerConfig.SERVER_CONFIG.getJsonFileOutput());
        Path filePath = path;
        JsonArray jsonArray = new JsonArray();
        JsonObject jsonObject = new JsonObject();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        if (!Files.notExists(filePath)) {
            try (FileReader fileReader = new FileReader(String.valueOf(filePath));
                 BufferedReader bufferedReader = new BufferedReader(fileReader)) {

                jsonArray = OneLevelJsonParser.parseOneLevelJsonToArray(bufferedReader);
            } catch (IOException e) {
                LogTools.exceptionLog("Не возможно прочитать файл.");
            }
        } else {
            try {
                Path dirPath = path.getParent();
                Files.createDirectories(dirPath);
                filePath = dirPath.resolve(String.valueOf(path.getFileName()));
                Files.createFile(filePath);
            } catch (IOException e) {
                LogTools.exceptionLog("Не удалось создать директорию для json файла.");
            }
        }

        for (OutputDataMarks key : map.keySet()) {
            String value = map.get(key);
            jsonObject.addProperty(key.getValue(), value);
        }
        jsonArray.add(jsonObject);
        try (FileWriter fileWriter = new FileWriter(String.valueOf(filePath));) {

            gson.toJson(jsonArray, fileWriter);
            fileWriter.flush();
        } catch (IOException e) {
            LogTools.exceptionLog("Не возможно записать файл.");
        }
    }

}
