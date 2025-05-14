import runnable.DataManager;
import runnable.DataOutputer;
import runnable.DataReceiver;

import java.io.IOException;

public class Server {

    public static void main(String[] args) throws IOException {
        DataReceiver dataReceiver = new DataReceiver();
        Thread thread1 = new Thread(dataReceiver);
        thread1.start();

        DataManager dataManager = new DataManager();
        Thread thread2 = new Thread(dataManager);
        thread2.start();

        DataOutputer dataOutputer = new DataOutputer();
        Thread thread3 = new Thread(dataOutputer);
        thread3.start();
    }

}