package example.framework;

public interface ErrorHandler {
    Response handleError(RequestMethod method, String lookupPath, Exception error);
}
