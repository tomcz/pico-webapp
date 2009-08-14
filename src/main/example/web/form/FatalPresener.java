package example.web.form;

import example.framework.Presenter;
import example.framework.Request;
import example.framework.Response;
import example.framework.RouteMapping;

@RouteMapping("/fatal")
public class FatalPresener implements Presenter {

    public Response display(Request request) {
        throw new UnsupportedOperationException();
    }
}
