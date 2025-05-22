package runnable;

import dataclasses.FullData;
import serves.LogTools;
import serves.ServerClientConnection;

import java.io.IOException;

import static source.SingletonServerConfig.SERVER_CONFIG;
import static storage.SingletonServerDataStorage.SERVER_DATA_STORAGE;

public class DataReceiver implements Runnable {

    @Override
    public void run() {
        while (true) {
            ServerClientConnection serverClientConnection;
            try {
                serverClientConnection = new ServerClientConnection();
                while (!SERVER_CONFIG.isInvalidConnection()) {
                    FullData fullData = serverClientConnection.receive();
                    if (fullData != null) {
                        SERVER_DATA_STORAGE.putFullDataPackToStorage(fullData);
                    }
                }
            } catch (IOException e) {
                LogTools.exceptionLog("Соединение прервано!");
            }
        }
    }

}
