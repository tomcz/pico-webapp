package example.domain.error;

import example.framework.Presenter;
import example.framework.Request;
import example.framework.Response;
import example.framework.RouteMapping;

@RouteMapping("/bad")
public class BadPresener implements Presenter {

    public Response display(Request request) {
        throw new UnsupportedOperationException();
    }
}
