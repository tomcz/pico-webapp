package example.framework;

public interface Request {

    String getParameter(String name);

    String getPathVariable(String name);

    Identity getIdentity(String name);
}
