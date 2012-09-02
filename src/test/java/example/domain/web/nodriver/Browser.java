package example.domain.web.nodriver;

import example.TestApplication;
import example.framework.Location;
import example.framework.RequestMethod;
import example.framework.test.TestRequestContext;
import example.framework.test.TestResponseContext;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Browser {

    private static TestApplication application;

    private String currentURI;

    public String currentURI() {
        return currentURI;
    }

    public <T> T get(String requestURI, Class<T> pageClass) {
        return send(new TestRequestContext(RequestMethod.GET, new Location(requestURI)), pageClass);
    }

    public <T> T send(TestRequestContext request, Class<T> pageClass) {
        try {
            TestResponseContext response = sendRequest(request, 0);
            return createPage(pageClass, response);

        } catch (Exception e) {
            handleException(e);
            return null;
        }
    }

    private TestResponseContext sendRequest(TestRequestContext request, int requestCount) throws Exception {
        if (requestCount > 5) {
            throw new IllegalStateException("Too many redirects to " + request.getLookupPath());
        }
        currentURI = request.getLookupPath();
        TestResponseContext response = application().process(request);
        String redirect = response.getRedirectURL();
        if (redirect != null) {
            response = sendRequest(new TestRequestContext(RequestMethod.GET, new Location(redirect)), requestCount + 1);
        }
        if (response.getErrorCode() > 0) {
            throw new IllegalStateException(currentURI + " --> " + response.getErrorCode());
        }
        return response;
    }

    private static TestApplication application() {
        if (application == null) {
            application = new TestApplication();
            application.registerShutdownHook();
        }
        return application;
    }

    private <T> T createPage(Class<T> pageClass, TestResponseContext response) throws Exception {
        HtmlPage htmlPage = new HtmlPage(response, this);
        Constructor<T> constructor = pageClass.getConstructor(htmlPage.getClass());
        return constructor.newInstance(htmlPage);
    }

    private void handleException(Throwable ex) {
        if (ex instanceof InvocationTargetException) {
            handleException(ex.getCause());

        } else if (ex instanceof RuntimeException) {
            throw (RuntimeException) ex;

        } else if (ex instanceof Error) {
            throw (Error) ex;

        } else {
            throw new RuntimeException(ex);
        }
    }
}
