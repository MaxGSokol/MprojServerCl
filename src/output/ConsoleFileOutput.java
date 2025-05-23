package output;

import serves.ClientData;

public class ConsoleFileOutput implements FileOutputType {

    @Override
    public void outputData(ClientData clientData) {
        System.out.println(clientData.getDate() + " " + clientData.getIp());
        for (String string : clientData.getData()) {
            System.out.println(string);
        }
        System.out.println("--------------------------------------");
    }

}
