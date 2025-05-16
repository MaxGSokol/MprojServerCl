package source;

import java.io.FileReader;
import java.util.Properties;

public class ServerClConfig {
    public static Properties SERVER_PROPERTIES = new Properties();
    private static final String PATH = "src/source/server.config";
    public static String CLIENT_IP;
    public static int SERVER_PORT;
    public static String LOG_PATH;
    public static String TEXT_FILE_OUTPUT;
    public static String JSON_FILE_OUTPUT;

    static {
        try (FileReader fileReader = new FileReader(PATH)) {
            SERVER_PROPERTIES.load(fileReader);
            SERVER_PORT = Integer.parseInt(SERVER_PROPERTIES.getProperty("port"));
            LOG_PATH = SERVER_PROPERTIES.getProperty("pathLog");
            TEXT_FILE_OUTPUT = SERVER_PROPERTIES.getProperty("pathText");
            JSON_FILE_OUTPUT = SERVER_PROPERTIES.getProperty("pathJson");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
