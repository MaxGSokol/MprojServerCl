package runnable;

import data.DataPack;
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
            DataPack dataPack = serverClientConnection.receiveAllotOfData();
            if (dataPack != null) {
                DataStorage.FULL_PACK_STORAGE.addFirst(dataPack);
            }
        }
    }

}
