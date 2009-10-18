package example.framework;

public class PresenterRoute implements Route {

    private final Presenter presenter;

    public PresenterRoute(Presenter presenter) {
        this.presenter = presenter;
    }

    public Response process(Request request) {
        return presenter.display(request);
    }
}
