package serves;

import data.DataPack;
import data.InputData;
import lombok.Getter;
import source.ServerClConfig;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class ServerClientConnection {
    private final ObjectOutputStream out;
    private final ObjectInputStream in;
    private final Socket clientSocket;
    public static volatile boolean IS_NOT_CONNECT;

    public ServerClientConnection() throws IOException {
        ServerSocket serverSocket = new ServerSocket(ServerClConfig.SERVER_PORT);
        LogTools.statusLog("В ожидании соединения.");
        clientSocket = serverSocket.accept();
        this.out = new ObjectOutputStream(clientSocket.getOutputStream());
        this.in = new ObjectInputStream(clientSocket.getInputStream());
        ServerClConfig.CLIENT_IP = String.valueOf(clientSocket.getInetAddress());
        IS_NOT_CONNECT = false;
        LogTools.statusLog("Соединение установлено.");
    }

    public DataPack receiveAllotOfData() {
        InputData inputData;
        DataPack dataPack = null;
        try {
            String userName = (String) in.readObject();
            String fileType = (String) in.readObject();
            String dataType = (String) in.readObject();
            String signature = (String) in.readObject();
            long dataLength = in.readLong();
            long controlSum = in.readLong();
            if (dataType.equals("SIMPLE")) {
                int simpleDate = in.readInt();
                inputData = new InputData(userName, fileType, simpleDate, dataType);
            } else {
                Map<String, Integer> dataMap = (ConcurrentHashMap<String, Integer>) in.readObject();
                inputData = new InputData(userName, fileType, dataMap, dataType);
            }
            dataPack = DataPack.builder()
                    .InputData(inputData)
                    .signature(signature)
                    .dataLength(dataLength)
                    .controlSum(controlSum).build();
        } catch (IOException e) {
            LogTools.exceptionLog("Соединение прервано.");
            IS_NOT_CONNECT = true;
        } catch (ClassNotFoundException e) {
            return null;
        }
        return dataPack;
    }

    public void close () {
        try {
            out.close();
            in.close();
            clientSocket.close();
            LogTools.statusLog("Соединение закрыто.");
        } catch (IOException e) {
            LogTools.exceptionLog("Не возможно закрыть соединение.");
        }
    }

}
