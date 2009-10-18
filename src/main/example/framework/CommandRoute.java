package example.framework;

public class CommandRoute implements Route {

    private final Command command;

    public CommandRoute(Command command) {
        this.command = command;
    }

    public Response process(Request request) {
        Redirect redirect = command.execute(request);
        return new RedirectResponse(redirect);
    }
}
