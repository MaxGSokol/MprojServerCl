package output;

import data.InputData;
import source.ServerClConfig;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConsoleFileOutput implements FileOutputType {
    InputData inputData;

    public ConsoleFileOutput(InputData inputData) {
        this.inputData = inputData;
    }

    @Override
    public void outputData() {
        System.out.println(outputFormater(inputData));
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
