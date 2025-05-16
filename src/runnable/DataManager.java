package runnable;

import data.DataPack;
import data.InputData;
import serves.LogTools;
import storage.DataStorage;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.zip.CRC32;

public class DataManager implements Runnable {
    @Override
    public void run() {

        while (true) {
            DataPack dataPack = DataStorage.FULL_PACK_STORAGE.pollLast();
            if (dataPack != null) {
                checkData(dataPack);
                DataStorage.INPUT_DATA_STORAGE.addFirst(dataPack.getInputData());
            }
        }
    }

    private void checkData(DataPack dataPack) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Проверка принятых данных.\n");
        stringBuilder.append("Сигнатура пакета данных -" + dataPack.getSignature() + " .\n");
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
