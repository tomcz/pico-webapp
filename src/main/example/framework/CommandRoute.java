package example.framework;

public class CommandRoute implements Route {

    private final Command command;
    private final URITemplate template;

    public CommandRoute(Command command, URITemplate template) {
        this.command = command;
        this.template = template;
    }

    public Class getHandlerType() {
        return command.getClass();
    }

    public URITemplate getTemplate() {
        return template;
    }

    public Response process(Request request) {
        Redirect redirect = command.execute(request);
        return new RedirectResponse(redirect);
    }
}
