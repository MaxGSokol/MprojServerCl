package data;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FullDataPack {
    private InputData inputData;
    private String signature;
    private long dataLength;
    private long controlSum;
}
