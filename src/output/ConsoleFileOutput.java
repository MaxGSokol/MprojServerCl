package output;

import serves.OutputDataMarks;

import java.util.TreeMap;

public class ConsoleFileOutput implements FileOutputType {

    @Override
    public void outputData(TreeMap<OutputDataMarks, String> map) {
        System.out.println(map.get(OutputDataMarks.DATE) + " " + map.get(OutputDataMarks.IP));
        System.out.println(map.get(OutputDataMarks.DATA));
        System.out.println("--------------------------------------");
    }

}
