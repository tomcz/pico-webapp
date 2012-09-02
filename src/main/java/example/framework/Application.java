package example.framework;

public interface Application {

    Response process(RequestContext request);

    void dispose();
}
