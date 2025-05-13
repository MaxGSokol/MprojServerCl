package runnable;

import data.FullDataPack;
import serves.DataType;
import serves.ServerClientConnection;

import java.io.IOException;

public class DataReceiver implements Runnable{
private ServerClientConnection serverClientConnection;
    @Override
    public void run() {
        try {
            serverClientConnection = new ServerClientConnection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

     /*   FullDataPack fullDataPack =  serverClientConnection.receive();
        if (fullDataPack == null) {
            System.out.println("!!!");
        }*/
        try {
            serverClientConnection.receiveAllotOfData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println(serverClientConnection.rec());
        serverClientConnection.send("Пакет данных успешно доставлен на сервер.");
        System.out.println("Сработало");

         //checkData(fullDataPack);


    }

    private void checkData(FullDataPack fullDataPack) {

      //  System.out.println("Сигнатура - " + fullDataPack.getSignature());
        System.out.println("Имя пользователя - "
                + fullDataPack.getInputDataPack().getUserName());
        System.out.println("Способ вывода данных на сервере - "
                + fullDataPack.getInputDataPack().getFileType().name());

        if (fullDataPack.getInputDataPack().getDataType() == DataType.ADVANCE) {
            System.out.println("Выбранные температурные режимы.");

            for (String key : fullDataPack.getInputDataPack().getDataMap().keySet()) {
                Integer value = fullDataPack.getInputDataPack().getDataMap().get(key);
                System.out.println(key + " - " + value + " градуса.");
            }
        }

        if (fullDataPack.getInputDataPack().getDataType() == DataType.SIMPLE) {
            System.out.println("Выбранный температурный режим - "
                    + fullDataPack.getInputDataPack().getSimpleData() + " градуса.");
        }

        System.out.println("Длинна данных в байтах - " + fullDataPack.getDataLength());
        System.out.println("CRC32 - " + fullDataPack.getControlSum());
    }

}
