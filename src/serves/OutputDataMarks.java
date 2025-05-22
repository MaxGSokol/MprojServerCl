package serves;

import lombok.Getter;

@Getter
public enum OutputDataMarks {
    DATE("date"),
    IP("ip"),
    DATA("data");

    private String value;

    OutputDataMarks(String value) {
        this.value = value;
    }

}
