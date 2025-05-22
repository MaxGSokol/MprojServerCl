package storage;

import dataclasses.FullData;
import dataclasses.InputData;

import java.util.ArrayDeque;

public enum SingletonServerDataStorage {
    SERVER_DATA_STORAGE;

    private volatile ArrayDeque<FullData> fullPackStorage;
    private volatile ArrayDeque<InputData> inputDataSStorage;

    SingletonServerDataStorage() {
        fullPackStorage = new ArrayDeque<>();
        inputDataSStorage = new ArrayDeque<>();
    }

    public void putInputDataToStorage(InputData inputData) {
        inputDataSStorage.addFirst(inputData);
    }

    public InputData getInputDataFromStorage() {
        return inputDataSStorage.pollLast();
    }

    public void putFullDataPackToStorage(FullData fullData) {
        fullPackStorage.addFirst(fullData);
    }

    public FullData getFullDataPackFromStorage() {
        return fullPackStorage.pollLast();
    }

}
