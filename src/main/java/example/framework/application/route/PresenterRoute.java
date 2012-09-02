package example.framework.application.route;

import example.framework.Presenter;
import example.framework.Request;
import example.framework.Response;
import example.framework.application.Route;

public class PresenterRoute implements Route {

    private final Presenter presenter;

    public PresenterRoute(Presenter presenter) {
        this.presenter = presenter;
    }

    public Response process(Request request) {
        return presenter.display(request);
    }
}
