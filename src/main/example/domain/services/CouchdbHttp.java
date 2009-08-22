package example.domain.services;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.UnhandledException;
import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class CouchdbHttp {

    private final Logger logger = Logger.getLogger(getClass());

    public String get(String url) {
        InputStream in = null;
        try {
            in = new URL(url).openStream();
            return read(in);

        } catch (FileNotFoundException e) {
            logger.debug("Resource not found at " + url);
            return null;

        } catch (IOException e) {
            throw new UnhandledException(e);

        } finally {
            IOUtils.closeQuietly(in);
        }
    }

    public String put(String url, String request) {
        OutputStream out = null;
        InputStream in = null;
        try {
            URL host = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) host.openConnection();

            conn.setDoInput(true);
            conn.setDoOutput(true);

            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Content-Length", String.valueOf(request.length()));

            out = conn.getOutputStream();
            write(out, request);

            in = conn.getInputStream();
            return read(in);

        } catch (IOException e) {
            throw new UnhandledException(e);

        } finally {
            IOUtils.closeQuietly(out);
            IOUtils.closeQuietly(in);
        }
    }

    private void write(OutputStream out, String request) throws IOException {
        if (logger.isDebugEnabled()) {
            logger.debug("Request: " + request);
        }
        IOUtils.write(request, out, "UTF-8");
    }

    private String read(InputStream in) throws IOException {
        String response = IOUtils.toString(in, "UTF-8");
        if (logger.isDebugEnabled()) {
            logger.debug("Response: " + response);
        }
        return response;
    }
}
