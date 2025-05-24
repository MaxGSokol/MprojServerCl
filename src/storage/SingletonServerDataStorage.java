package storage;

import dataclasses.InputData;

import java.util.ArrayDeque;

public enum SingletonServerDataStorage {
    SERVER_DATA_STORAGE;

    private volatile ArrayDeque<Object> fullPackStorage;
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

    public void putFullDataPackToStorage(Object fullData) {
        fullPackStorage.addFirst(fullData);
    }

    public Object getFullDataPackFromStorage() {
        return fullPackStorage.pollLast();
    }

}
