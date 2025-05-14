package serves;

import data.FullDataPack;
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

    public ServerClientConnection() throws IOException {
        ServerSocket serverSocket = new ServerSocket(ServerClConfig.SERVER_PORT);
        clientSocket = serverSocket.accept();
        this.out = new ObjectOutputStream(clientSocket.getOutputStream());
        this.in = new ObjectInputStream(clientSocket.getInputStream());
        LogTools.statusLog("Соединение установлено.");
    }

    public FullDataPack receiveAllotOfData() {
        FullDataPack fullDataPack;
        InputData inputData;
        try {
            String userName = (String) in.readObject();
            String fileType = (String) in.readObject();
            String dataType = (String) in.readObject();
            String signature = (String) in.readObject();
            long dataLength = in.readLong();
            long controlSum = in.readLong();
            if (dataType.equals("SIMPLE")) {
                int simpleDate = in.readInt();
                inputData = new InputData(
                        userName,
                        fileType,
                        simpleDate,
                        dataType,
                        String.valueOf(clientSocket.getInetAddress())
                );
            } else {
                Map<String, Integer> dataMap = (ConcurrentHashMap<String, Integer>) in.readObject();
                inputData = new InputData(
                        userName,
                        fileType,
                        dataMap,
                        dataType,
                        String.valueOf(clientSocket.getInetAddress())
                );
            }
            fullDataPack = FullDataPack.builder()
                    .inputData(inputData)
                    .signature(signature)
                    .dataLength(dataLength)
                    .controlSum(controlSum).build();
            return fullDataPack;
        } catch (IOException | ClassNotFoundException e) {
            LogTools.exceptionLog("Не удалось принять данные");
            throw new RuntimeException();
        }
    }

    public void send(String string) {
        try {
            out.writeObject(string);
        } catch (IOException e) {
            LogTools.exceptionLog("Не удалось отправить сообщение.");
        }
    }

}
