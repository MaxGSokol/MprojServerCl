package serves;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class ClientData implements Serializable {
    private final String date;
    private final String ip;
    private final String[] data;

    public ClientData(String date, String ip, String[] data) {
        this.date = date;
        this.ip = ip;
        this.data = data;
    }

}
