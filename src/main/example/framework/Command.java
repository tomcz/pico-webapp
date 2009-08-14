package example.framework;

public interface Command {
    Redirect execute(Request request);
}
