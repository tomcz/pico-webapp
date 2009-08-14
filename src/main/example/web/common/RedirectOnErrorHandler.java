package example.web.common;

import example.framework.ErrorHandler;
import example.framework.RedirectResponse;
import example.framework.RequestMethod;
import example.framework.Response;
import example.framework.Redirect;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;

public class RedirectOnErrorHandler implements ErrorHandler {

    private static final String MESSAGE = "Unexpected error [%s] while processing %s %s: %s";

    private final Logger logger = Logger.getLogger(getClass());

    public Response handleError(RequestMethod method, String lookupPath, Exception error) {
        String errorRef = RandomStringUtils.randomAlphanumeric(7).toUpperCase();
        logger.error(String.format(MESSAGE, errorRef, method, lookupPath, error), error);
        return new RedirectResponse(new Redirect(ErrorReferencePresenter.class, "errorRef", errorRef));
    }
}
