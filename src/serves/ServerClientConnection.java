package serves;

import data.FullDataPack;
import storage.ServerClConfig;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServerClientConnection {
    private  Socket clientSocket;
    private ServerSocket serverSocket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public ServerClientConnection() throws IOException {
        this.serverSocket = new ServerSocket(ServerClConfig.SERVER_PORT);
        this.clientSocket = serverSocket.accept();
        this.out = new ObjectOutputStream(clientSocket.getOutputStream());
        this.in = new ObjectInputStream(clientSocket.getInputStream());
        LogTools.statusLog("Соединение установлено.");
    }

    public FullDataPack receive() {
        FullDataPack fullDataPack = null;
        try {
            fullDataPack = (FullDataPack) in.readObject();
        } catch (IOException e) {
LogTools.exceptionLog("IOException");
        } catch (ClassNotFoundException e) {
LogTools.exceptionLog("ClassNotFoundException");
        }
        LogTools.statusLog("Объект получен.");
        return fullDataPack;
    }

    public void receiveAllotOfData() throws IOException, ClassNotFoundException {
        String userName = (String) in.readObject();
        String fileType = (String) in.readObject();
        String dataType = (String) in.readObject();
        String signature = (String) in.readObject();
        long dataLength = in.readLong();
        long controlSum = in.readLong();
        System.out.println(userName + "\n" + fileType + "\n" + dataType + "\n" + signature
        + "\n" + dataLength + "\n" + controlSum);
        if (dataType.equals("SIMPLE")) {
            int simpleDate = in.readInt();
            System.out.println(simpleDate);
        } else {
            Map<String,Integer> dataMap = (ConcurrentHashMap<String, Integer>) in.readObject();

        for (String key : dataMap.keySet()) {
            Integer value = dataMap.get(key);
            System.out.println(key + " - " + value + " градуса.");
        }
        }
    }

    public String rec() {
        String string = null;
        try {
            string = (String) in.readObject();
        } catch (IOException e) {
            LogTools.exceptionLog("IOException");
        } catch (ClassNotFoundException e) {
            LogTools.exceptionLog("ClassNotFoundException");
        }
        LogTools.statusLog("Объект получен.");
        return string;
    }

    public void send(String string) {
        try {
            out.writeObject(string);
        } catch (IOException e) {
            LogTools.exceptionLog("Не удалось отправить сообщение.");
        }
    }
}
