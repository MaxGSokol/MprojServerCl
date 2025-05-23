package serves;

import lombok.Getter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import static source.SingletonServerConfig.SERVER_CONFIG;

@Getter
public class ServerClientConnection implements Runnable {
    private final ObjectOutputStream out;
    private final ObjectInputStream in;
    private final Socket clientSocket;

    public ServerClientConnection() {
        try (ServerSocket serverSocket = new ServerSocket(SERVER_CONFIG.getServerPort())) {
            LogTools.statusLog("В ожидании соединения.");
            clientSocket = serverSocket.accept();
            this.out = new ObjectOutputStream(clientSocket.getOutputStream());
            this.in = new ObjectInputStream(clientSocket.getInputStream());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        SERVER_CONFIG.setClientIp(String.valueOf(clientSocket.getInetAddress()));
        SERVER_CONFIG.setInvalidConnection(false);
        Thread thread = new Thread(this);
        thread.setDaemon(true);
        thread.start();
        LogTools.statusLog("Соединение установлено.");
    }

    public Object receive() throws IOException {
        try {
            return in.readObject();
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    private void close() {
        try {
            out.close();
            in.close();
            clientSocket.close();
            LogTools.statusLog("Потоки ввода вывода закрыты.");
        } catch (IOException e) {
            LogTools.exceptionLog("Не возможно закрыть соединение.");
        }
    }

    @Override
    public void run() {
        while (!SERVER_CONFIG.isInvalidConnection()) {
        }
        close();
    }

}
