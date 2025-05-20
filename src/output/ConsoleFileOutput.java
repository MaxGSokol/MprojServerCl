package output;

import java.util.HashMap;

public class ConsoleFileOutput implements FileOutputType {


    @Override
    public void outputData(HashMap<String, String> map) {
        System.out.println(map.get("date") + " " + map.get("ip"));
        System.out.println(map.get("data"));
        System.out.println("--------------------------------------");
    }

}
