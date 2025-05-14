package output;

import serves.LogTools;
import source.ServerClConfig;

import java.io.FileWriter;
import java.io.IOException;

public class DefaultFileOutput implements FileOutputType {

    @Override
    public void outputData(String data) {
        try (FileWriter fileWriter = new FileWriter(ServerClConfig.TEXT_FILE_OUTPUT, true)) {
            fileWriter.write(data);
        } catch (IOException e) {
            LogTools.exceptionLog("Невозможно записать данные в файл.");
        }
    }

}
