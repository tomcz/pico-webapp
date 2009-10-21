package example.framework;

public class StatusCode {

    private final int code;
    private final String message;

    private Cookies cookies;
    private Headers headers;

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

    public Cookies getCookies() {
        return cookies;
    }

    public void setCookies(Cookies cookies) {
        this.cookies = cookies;
    }

    public Headers getHeaders() {
        return headers;
    }

    public void setHeaders(Headers headers) {
        this.headers = headers;
    }
}
