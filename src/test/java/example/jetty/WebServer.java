package example.jetty;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;

import java.util.List;

import static ch.lambdaj.collection.LambdaCollections.with;
import static org.hamcrest.Matchers.endsWith;

public class WebServer {

    private final Server server;

    public WebServer(int port) {
        server = new Server(port);
    }

    public WebServer start() throws Exception {
        WebAppContext context = new WebAppContext("src/main/webapp", "/example");
        server.addHandler(withoutTaglibs(context));
        server.start();
        return this;
    }

    private WebAppContext withoutTaglibs(WebAppContext context) {
        String[] configurationClasses = context.getConfigurationClasses();
        List<String> withoutTaglibs = with(configurationClasses).remove(endsWith("TagLibConfiguration"));
        context.setConfigurationClasses(withoutTaglibs.toArray(new String[withoutTaglibs.size()]));
        return context;
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
