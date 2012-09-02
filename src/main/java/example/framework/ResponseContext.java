package example.framework;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Map;

public interface ResponseContext {

    String getContextPath();

    String getServletPath();

    Map<String, Object> getAttributes();

    void setHeader(Header header);

    void sendError(int errorCode) throws IOException;

    void sendError(int errorCode, String message) throws IOException;

    void sendRedirect(String url) throws IOException;

    void setStatusCode(int code);

    void setContentType(String contentType);

    void setCharacterEncoding(String charset);

    PrintWriter getWriter() throws IOException;

    OutputStream getOutputStream() throws IOException;
}
