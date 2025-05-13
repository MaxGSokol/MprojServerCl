package data;

import java.io.Serializable;
@Data
public class FullDataPack implements Serializable {
    private static String SIGNATURE;
    private InputDataPack INPUT_DATA_PACK;
    private long DATA_LENGTH;
    private long CONTROL_SUM;

    public FullDataPack(InputDataPack INPUT_DATA_PACK, long DATA_LENGTH, long CONTROL_SUM) {
        this.INPUT_DATA_PACK = INPUT_DATA_PACK;
        this.DATA_LENGTH = DATA_LENGTH;
        this.CONTROL_SUM = CONTROL_SUM;
    }

    public static String getSignature() {
        return SIGNATURE;
    }

    public InputDataPack getInputDataPack() {
        return INPUT_DATA_PACK;
    }

    public long getDataLength() {
        return DATA_LENGTH;
    }

    public long getControlSum() {
        return CONTROL_SUM;
    }
}
