package output;

import serves.ClientData;
import serves.LogTools;
import source.SingletonServerConfig;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DefaultFileOutput implements FileOutputType {

    @Override
    public void outputData(ClientData clientData) {
        Path path = Path.of(SingletonServerConfig.SERVER_CONFIG.getTextFileOutput());
        Path filePath = path;
        if (Files.notExists(path)) {
            try {
                Path dirPath = path.getParent();
                Files.createDirectories(dirPath);
                filePath = dirPath.resolve(String.valueOf(path.getFileName()));
                Files.createFile(filePath);
            } catch (IOException e) {
                LogTools.exceptionLog("Не удалось создать директорию для json файла.");
            }
        }

        try (FileWriter fileWriter = new FileWriter(String.valueOf(filePath), true)) {
            fileWriter.write(clientData.getDate() + " " + clientData.getIp() + "\n");
            for (String string : clientData.getData()) {
                fileWriter.write(string + "\n");
            }
            fileWriter.write("--------------------------------------\n");
            fileWriter.flush();
        } catch (IOException e) {
            LogTools.exceptionLog("Невозможно записать данные в файл.");
        }
    }

}

