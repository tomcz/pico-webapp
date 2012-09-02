package example.framework.application;

import example.framework.Request;
import example.framework.Response;

public interface Route {

    Response process(Request request);
}
