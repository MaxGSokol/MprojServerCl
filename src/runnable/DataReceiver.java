package runnable;

import data.FullDataPack;
import serves.ServerClientConnection;
import storage.DataStorage;

import java.io.IOException;

public class DataReceiver implements Runnable {
    private final ServerClientConnection serverClientConnection;

    public DataReceiver() throws IOException {
        this.serverClientConnection = new ServerClientConnection();
    }

    @Override
    public void run() {

        while (true) {
            FullDataPack fullDataPack = serverClientConnection.receiveAllotOfData();
            if (fullDataPack == null) {
                continue;
            }
            DataStorage.FULL_PACK_STORAGE.addFirst(fullDataPack);
            serverClientConnection.send("Пакет данных успешно доставлен на сервер.");
        }
    }

}
