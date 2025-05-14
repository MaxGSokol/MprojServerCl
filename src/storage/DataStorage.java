package storage;

import data.FullDataPack;
import data.InputData;

import java.util.ArrayDeque;

public class DataStorage {
    public static volatile ArrayDeque<FullDataPack> FULL_PACK_STORAGE = new ArrayDeque<>();
    public static volatile ArrayDeque<InputData> INPUT_DATA_STORAGE = new ArrayDeque<>();
}
