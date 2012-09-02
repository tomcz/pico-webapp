package example.jetty;

import com.google.common.base.Predicate;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;

import static com.google.common.collect.FluentIterable.from;
import static com.google.common.collect.Lists.newArrayList;

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

        configurationClasses = from(newArrayList(configurationClasses))
                .filter(tagLibConfiguration())
                .toArray(String.class);

        context.setConfigurationClasses(configurationClasses);
        return context;
    }

    private Predicate<String> tagLibConfiguration() {
        return new Predicate<String>() {
            public boolean apply(String className) {
                return !className.endsWith("TagLibConfiguration");
            }
        };
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
