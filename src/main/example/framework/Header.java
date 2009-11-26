package example.framework;

public class Header {

    private Cookies cookies;
    private HeaderFields fields;

    public Cookies getCookies() {
        return cookies;
    }

    public void setCookies(Cookies cookies) {
        this.cookies = cookies;
    }

    public void addCookie(String name, String value) {
        if (cookies == null) {
            cookies = new Cookies();
        }
        cookies.addCookie(name, value);
    }

    public HeaderFields getFields() {
        return fields;
    }

    public void setFields(HeaderFields fields) {
        this.fields = fields;
    }

    public void addHeaderField(String name, String value) {
        if (fields == null) {
            fields = new HeaderFields();
        }
        fields.add(name, value);
    }
}
