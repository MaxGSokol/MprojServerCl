package data;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DataPack {
    private InputData InputData;
    private String signature;
    private long dataLength;
    private long controlSum;
}
