package source;

import lombok.Getter;
import lombok.Setter;

import java.io.FileReader;
import java.util.Properties;

@Getter
public enum SingletonServerConfig {
    SERVER_CONFIG;

    @Setter
    private volatile boolean isInvalidConnection;
    @Setter
    private volatile String clientIp;
    private final int serverPort;
    private final String signature;
    private final String logPath;
    private final String textFileOutput;
    private final String jsonFileOutput;


    SingletonServerConfig() {
        Properties serverProperties = new Properties();
        try (FileReader fileReader = new FileReader("src/source/server.config")) {
            serverProperties.load(fileReader);
            serverPort = Integer.parseInt(serverProperties.getProperty("port"));
            logPath = serverProperties.getProperty("pathLog");
            signature = serverProperties.getProperty("signature");
            textFileOutput = serverProperties.getProperty("pathText");
            jsonFileOutput = serverProperties.getProperty("pathJson");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
