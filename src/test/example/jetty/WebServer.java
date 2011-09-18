package example.jetty;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;

public class WebServer {

    private final Server server;

    public WebServer(int port) {
        server = new Server(port);
    }

    public WebServer start() throws Exception {
        server.addHandler(new WebAppContext("src/webapp", "/example"));
        server.start();
        return this;
    }

    public WebServer graceful() {
        server.setGracefulShutdown(1000);
        server.setStopAtShutdown(true);
        return this;
    }

    public void stop() throws Exception {
        server.stop();
    }

    public static void main(String[] args) throws Exception {
        new WebServer(8080).graceful().start();
    }
}
