package runnable;

import data.DataPack;
import serves.ServerClientConnection;
import storage.DataStorage;

import java.io.IOException;

public class DataReceiver implements Runnable {

    @Override
    public void run() {

        while (true) {
            ServerClientConnection serverClientConnection;
            try {
                serverClientConnection = new ServerClientConnection();
                while (!ServerClientConnection.IS_NOT_CONNECT) {

                    DataPack dataPack = serverClientConnection.receiveAllotOfData();
                    if (dataPack != null) {
                        DataStorage.FULL_PACK_STORAGE.addFirst(dataPack);
                    }
                }
                serverClientConnection.close();
            } catch (IOException e) {
                continue;
            }
        }
    }

}
