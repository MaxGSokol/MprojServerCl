package storage;

import java.io.FileReader;
import java.util.Properties;

public class ServerClConfig {
    public static Properties SERVER_PROPERTIES = new Properties();
    private static final String PATH = "src/server.config";
    public static int SERVER_PORT;

    static {
        try (FileReader fileReader = new FileReader(PATH)) {
            SERVER_PROPERTIES.load(fileReader);
            SERVER_PORT = Integer.parseInt(SERVER_PROPERTIES.getProperty("port"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
