package runnable;

import data.DataPack;
import serves.ServerClientConnection;
import source.SingletonServerConfig;
import storage.SingletonServerDataStorage;

import java.io.IOException;

public class DataReceiver implements Runnable {

    @Override
    public void run() {

        while (true) {
            ServerClientConnection serverClientConnection;
            try {
                serverClientConnection = new ServerClientConnection();
                while (!SingletonServerConfig.SERVER_CONFIG.isInvalidConnection()) {
                    DataPack dataPack = serverClientConnection.receiveAllotOfData();
                    if (dataPack != null) {
                        SingletonServerDataStorage.SERVER_DATA_STORAGE.putFullDataPackToStorage(dataPack);
                    }
                }
            } catch (IOException e) {
                continue;
            }
        }
    }

}
