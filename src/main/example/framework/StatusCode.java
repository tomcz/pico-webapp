package example.framework;

public class StatusCode extends Header {

    private final int code;

    public StatusCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
