package example.framework.application.error;

import example.framework.ContainerException;
import example.framework.ErrorHandler;
import example.framework.Redirect;
import example.framework.RequestMethod;
import example.framework.Response;
import example.framework.application.ErrorResponse;
import example.framework.application.route.RedirectResponse;
import example.framework.template.TemplateException;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RedirectOnErrorHandler implements ErrorHandler {

    private static final String MESSAGE = "Unexpected error [%s] while processing %s %s: %s";

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public Response handleError(RequestMethod method, String lookupPath, Exception error) {
        String errorRef = RandomStringUtils.randomAlphanumeric(7).toUpperCase();
        logger.error(String.format(MESSAGE, errorRef, method, lookupPath, error), error);

        // these exceptions are fatal, no point in redirecting because we'll wind up here again
        if ((error instanceof TemplateException) || (error instanceof ContainerException)) {
            return ErrorResponse.internalError("Error: " + errorRef);
        }
        return new RedirectResponse(new Redirect(ErrorReferencePresenter.class, "errorRef", errorRef));
    }
}
