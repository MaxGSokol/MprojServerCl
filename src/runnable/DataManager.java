package runnable;

import dataclasses.FullData;
import dataclasses.InputData;
import dataenums.OutputFileType;
import serves.LogTools;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.InputMismatchException;
import java.util.zip.CRC32;


import static source.SingletonServerConfig.SERVER_CONFIG;
import static storage.SingletonServerDataStorage.SERVER_DATA_STORAGE;

public class DataManager implements Runnable {
    @Override
    public void run() {
        while (true) {
            FullData fullData;

            try {
                fullData = (FullData) SERVER_DATA_STORAGE.getFullDataPackFromStorage();
            } catch (ClassCastException r) {
                LogTools.exceptionLog("Полученный пакет данных не соответствует " +
                        "формату обрабатываемому сервером.");
                fullData = null;
            }

            if (fullData != null) {
                try {
                    checkData(fullData);
                } catch (InputMismatchException e) {
                    SERVER_CONFIG.setInvalidConnection(true);
                    continue;
                }
                SERVER_DATA_STORAGE.putInputDataToStorage(fullData.getInputData());
            }
        }
    }

    private void checkData(FullData fullData) throws InputMismatchException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Проверка принятых данных.\n");
        if (fullData.getSignature().equals(SERVER_CONFIG.getSignature())) {
            stringBuilder.append("Сигнатура пакета данных соответствует установленной.\n");
        } else {
            LogTools.exceptionLog("Данные поступили из неизвестного источника.\n" +
                    "Соединение будет прерванною!");
            throw new InputMismatchException();
        }
        if (!(fullData.getInputData().getFileType().equals(OutputFileType.CONSOLE) ||
                fullData.getInputData().getFileType().equals(OutputFileType.PLAIN) ||
                fullData.getInputData().getFileType().equals(OutputFileType.JSON))) {
            LogTools.exceptionLog("Способ дальнейшей обработки данных не соответствует параметрам программы. \n" +
                    "Соединение будет прервано!");
            throw new InputMismatchException();
        }
        if (fullData.getDataLength() == getDataLength(getDataBytesArray(fullData.getInputData()))) {
            stringBuilder.append("Длинна данных соответствует длине данных при отправлении.\n");
        } else {
            stringBuilder.append("Данные получены не полностью. Возможна потеря при передаче или получении.\n");
        }
        if (fullData.getControlSum() == getCRC32(getDataBytesArray(fullData.getInputData()))) {
            stringBuilder.append("Контрольная сумма \"CRC32\" соответствует контрольной сумме при отправлении.\n");
        } else {
            stringBuilder.append("Контрольная сумма \"CRC32\" не соответствует контрольной сумме при отправлении" +
                    "Данные повреждены.\n");
        }
        stringBuilder.append("---------------------------------------\n");

        LogTools.statusLog(stringBuilder.toString());
    }

    private byte[] getDataBytesArray(InputData inputData) {
        byte[] obj = null;
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ObjectOutputStream outputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            outputStream.writeObject(inputData);
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
