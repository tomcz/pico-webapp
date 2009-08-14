package example.framework;

public class PresenterRoute implements Route {

    private final Presenter presenter;
    private final URITemplate template;

    public PresenterRoute(Presenter presenter, URITemplate template) {
        this.presenter = presenter;
        this.template = template;
    }

    public Class getHandlerType() {
        return presenter.getClass();
    }

    public URITemplate getTemplate() {
        return template;
    }

    public Response process(Request request) {
        return presenter.display(request);
    }
}
