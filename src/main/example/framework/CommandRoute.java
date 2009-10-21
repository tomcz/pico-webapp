package example.framework;

public class CommandRoute implements Route {

    private final Command command;

    public CommandRoute(Command command) {
        this.command = command;
    }

    public Response process(Request request) {
        Header header = command.execute(request);
        if (header instanceof Redirect) {
            return new RedirectResponse((Redirect) header);
        }
        if (header instanceof StatusCode) {
            return new StatusCodeResponse((StatusCode) header);
        }
        return new HeaderResponse(header);
    }
}
