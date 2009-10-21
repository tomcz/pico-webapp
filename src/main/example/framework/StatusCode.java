package example.framework;

public class StatusCode extends Header {

    private final int code;
    private final String message;

    public StatusCode(int code) {
        this(code, null);
    }

    public StatusCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
