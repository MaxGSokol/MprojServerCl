package storage;

import data.DataPack;
import data.InputData;

import java.util.ArrayDeque;

public class DataStorage {
    public static volatile ArrayDeque<DataPack> FULL_PACK_STORAGE = new ArrayDeque<>();
    public static volatile ArrayDeque<InputData> INPUT_DATA_STORAGE = new ArrayDeque<>();
}
