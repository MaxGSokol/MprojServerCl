package runnable;

import data.DataPack;
import data.InputData;
import serves.LogTools;
import source.SingletonServerConfig;
import storage.SingletonServerDataStorage;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.zip.CRC32;

public class DataManager implements Runnable {
    @Override
    public void run() {
        while (true) {
            DataPack dataPack =
                    SingletonServerDataStorage.SERVER_DATA_STORAGE.getFullDataPackFromStorage();
            if (dataPack != null) {
                if (!checkData(dataPack)) {
                    SingletonServerConfig.SERVER_CONFIG.setInvalidConnection(true);
                    continue;
                }
                SingletonServerDataStorage.SERVER_DATA_STORAGE.putInputDataToStorage(dataPack.getInputData());
            }
        }
    }

    private boolean checkData(DataPack dataPack) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Проверка принятых данных.\n");
        if (dataPack.getSignature().equals(SingletonServerConfig.SERVER_CONFIG.getSignature())) {
            stringBuilder.append("Сигнатура пакета данных соответсвует установленной.\n");
        } else {
            LogTools.exceptionLog("Данные поступили из неивестного источника.\n" +
                    "Соединение будет прерванно!");
            return false;
        }
        if (!(dataPack.getInputData().getFileType().equals("CONSOLE") ||
                dataPack.getInputData().getFileType().equals("PLAIN") ||
                dataPack.getInputData().getFileType().equals("JSON"))) {
            LogTools.exceptionLog("Способ дальнейшей обработки данных не соответствует параметрам программы. \n" +
                    "Соединение будет прервано!");
            SingletonServerConfig.SERVER_CONFIG.setInvalidConnection(true);
            return false;
        }
        if (dataPack.getDataLength() == getDataLength(getDataBytesArray(dataPack.getInputData()))) {
            stringBuilder.append("Длинна данных соответствует длинне данных при отправлении.\n");
        } else {
            stringBuilder.append("Данные получены не полностью. Возможна потеря при передаче или получении.\n");
        }
        if (dataPack.getControlSum() == getCRC32(getDataBytesArray(dataPack.getInputData()))) {
            stringBuilder.append("Контрольная сумма \"CRC32\" соответствует контрольной сумме при отправлении.\n");
        } else {
            stringBuilder.append("Контрольная сумма \"CRC32\" не соответствует конртрольной сумме при отправлении" +
                    "Данные повреждены.\n");
        }
        stringBuilder.append("---------------------------------------\n");

        LogTools.statusLog(stringBuilder.toString());
        return true;
    }

    private byte[] getDataBytesArray(InputData inputData) {
        byte[] obj = null;
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ObjectOutputStream outputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            outputStream.writeObject(inputData.getUserName());
            if (inputData.getDataType().equals("SIMPLE")) {
                outputStream.writeInt(inputData.getSimpleData());
            } else {
                outputStream.writeObject(inputData.getDataMap());
            }
            outputStream.flush();
            obj = byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            LogTools.exceptionLog("Невозможно создать массив байт.");
        }
        return obj;
    }

    private long getDataLength(byte[] obj) {
        return obj.length;
    }

    private long getCRC32(byte[] obj) {
        CRC32 crc32 = new CRC32();
        crc32.update(obj);
        return crc32.getValue();
    }

}
