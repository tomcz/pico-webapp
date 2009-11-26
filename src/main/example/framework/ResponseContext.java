package example.framework;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Map;

public interface ResponseContext {

    Map<String, Object> getAttributes();

    String getContextPath();

    String getServletPath();

    void setHeader(Header header);

    void sendError(int errorCode) throws IOException;

    void sendError(int code, String message) throws IOException;

    void sendRedirect(String url) throws IOException;

    void setContentType(String contentType);

    void setCharacterEncoding(String charset);

    PrintWriter getWriter() throws IOException;

    OutputStream getOutputStream() throws IOException;
}
