package storage;

import data.DataPack;
import data.InputData;

import java.util.ArrayDeque;

public enum SingletonServerDataStorage {
    SERVER_DATA_STORAGE;

    private volatile ArrayDeque<DataPack> fullPackStorage;
    private volatile ArrayDeque<InputData> inputDataStorage;

    SingletonServerDataStorage() {
        fullPackStorage = new ArrayDeque<>();
        inputDataStorage = new ArrayDeque<>();
    }

    public void putInputDataToStorage(InputData inputData) {
        inputDataStorage.addFirst(inputData);
    }

    public InputData getInputDataFromStorage() {
        return inputDataStorage.pollLast();
    }

    public void putFullDataPackToStorage(DataPack dataPack) {
        fullPackStorage.addFirst(dataPack);
    }

    public DataPack getFullDataPackFromStorage() {
        return fullPackStorage.pollLast();
    }

}
