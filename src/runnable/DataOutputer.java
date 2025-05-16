package runnable;

import data.InputData;
import output.ConsoleFileOutput;
import output.DefaultFileOutput;
import output.FileOutputType;
import output.JsonFileOutput;
import storage.DataStorage;

public class DataOutputer implements Runnable {

    @Override
    public void run() {

        while (true) {
            InputData InputData = DataStorage.INPUT_DATA_STORAGE.pollLast();
            if (InputData != null) {
                FileOutputType fileOutputType = outputFactory(InputData);
                fileOutputType.outputData();
            }
        }
    }

    private FileOutputType outputFactory(InputData inputData) {
        return switch (inputData.getFileType()) {
            case "CONSOLE" -> new ConsoleFileOutput(inputData);
            case "PLAIN" -> new DefaultFileOutput(inputData);
            case "JSON" -> new JsonFileOutput(inputData);
            default -> null;
        };
    }
}
