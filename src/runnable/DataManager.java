package runnable;

import data.FullDataPack;
import storage.DataStorage;

public class DataManager implements Runnable {
    @Override
    public void run() {

        while (true) {
            FullDataPack fullDataPack = DataStorage.FULL_PACK_STORAGE.pollLast();
            if (fullDataPack == null) {
                continue;
            }
            DataStorage.INPUT_DATA_STORAGE.addFirst(fullDataPack.getInputData());
        }
    }

}
