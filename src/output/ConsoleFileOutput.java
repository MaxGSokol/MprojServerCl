package output;

public class ConsoleFileOutput implements FileOutputType {

    @Override
    public void outputData(String data) {
        System.out.println(data);
    }

}
