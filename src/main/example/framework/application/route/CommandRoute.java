package example.framework.application.route;

import example.framework.Command;
import example.framework.Header;
import example.framework.Redirect;
import example.framework.Request;
import example.framework.Response;
import example.framework.StatusCode;
import example.framework.application.Route;

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
