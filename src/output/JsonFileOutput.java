package output;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import serves.ClientData;
import serves.LogTools;
import source.SingletonServerConfig;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonFileOutput implements FileOutputType {

    @Override
    public void outputData(ClientData clientData) {
        Path path = Path.of(SingletonServerConfig.SERVER_CONFIG.getJsonFileOutput());
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        ArrayList<ClientData> clientDataList = new ArrayList<>();

        if (!Files.notExists(path)) {
            clientDataList = getClientDataArrayFromJson(path, gson);
        } else {
            getNewDirectory(path);
        }
        clientDataList.add(clientData);
        writeDataToJson(path, clientDataList, gson);


    }

    private ArrayList<ClientData> getClientDataArrayFromJson(Path path, Gson gson) {
        ArrayList<ClientData> clientDataArrayList = new ArrayList<>();

        try (FileReader fileReader = new FileReader(String.valueOf(path));
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            try {
                List<ClientData> dataListataList =
                        Arrays.asList(gson.fromJson(bufferedReader, ClientData[].class));
                clientDataArrayList.addAll(dataListataList);
            } catch (NullPointerException c) {
                return new ArrayList<>();
            } catch (JsonSyntaxException d) {
                LogTools.exceptionLog("Данные в файле повреждены и будут утеряны.\n" +
                        "Файл будет перезаписан новыми данными.");
                return new ArrayList<>();
            }
        } catch (IOException e) {
            LogTools.exceptionLog("Не возможно прочитать файл.");
        }
        return clientDataArrayList;
    }

    private void writeDataToJson(Path path, ArrayList<ClientData> clientDataList, Gson gson) {
        try (FileWriter fileWriter = new FileWriter(String.valueOf(path))) {
            gson.toJson(clientDataList, fileWriter);
            fileWriter.flush();
        } catch (IOException e) {
            LogTools.exceptionLog("Не возможно записать файл.");
        }
    }

    private void getNewDirectory(Path path) {
        try {
            Path dirPath = path.getParent();
            Files.createDirectories(dirPath);
            Path newFilePath = dirPath.resolve(String.valueOf(path.getFileName()));
            Files.createFile(newFilePath);
        } catch (IOException e) {
            LogTools.exceptionLog("Не удалось создать директорию для json файла.");
        }
    }
}
