package example.framework;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletResponse;

public class RouteFinderTests {

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToRegisterHandlerTypeWithoutAnnotation() throws Exception {
        RouteFinder finder = new RouteFinder();
        finder.registerRoute(String.class);
    }

    @Test
    public void shouldCreatePresenterRouteForGetRequest() throws Exception {
        Container scope = mock(Container.class);
        when(scope.get(TestPresenter.class)).thenReturn(new TestPresenter());

        RouteFinder finder = new RouteFinder();
        finder.registerRoute(TestPresenter.class);

        Route route = finder.findRoute(RequestMethod.GET, "/test", scope);

        assertThat(route, instanceOf(PresenterRoute.class));
        assertEquals(TestPresenter.class, route.getHandlerType());
        assertEquals(new URITemplate("/test"), route.getTemplate());
    }

    @Test
    public void shouldCreateCommandRouteForPostRequest() throws Exception {
        Container scope = mock(Container.class);
        when(scope.get(TestCommand.class)).thenReturn(new TestCommand());

        RouteFinder finder = new RouteFinder();
        finder.registerRoute(TestCommand.class);

        Route route = finder.findRoute(RequestMethod.POST, "/test", scope);

        assertThat(route, instanceOf(CommandRoute.class));
        assertEquals(TestCommand.class, route.getHandlerType());
        assertEquals(new URITemplate("/test"), route.getTemplate());
    }

    @Test
    public void shouldCreatePresenterRouteForGetRequestWhenHaveCommandAndPresenerMappedToSamePath() throws Exception {
        Container scope = mock(Container.class);
        when(scope.get(TestPresenter.class)).thenReturn(new TestPresenter());

        RouteFinder finder = new RouteFinder();
        finder.registerRoute(TestPresenter.class);
        finder.registerRoute(TestCommand.class);

        Route route = finder.findRoute(RequestMethod.GET, "/test", scope);

        assertThat(route, instanceOf(PresenterRoute.class));
        assertEquals(TestPresenter.class, route.getHandlerType());
        assertEquals(new URITemplate("/test"), route.getTemplate());
    }

    @Test
    public void shouldCreateCommandRouteForPostRequestWhenHaveCommandAndPresenerMappedToSamePath() throws Exception {
        Container scope = mock(Container.class);
        when(scope.get(TestCommand.class)).thenReturn(new TestCommand());

        RouteFinder finder = new RouteFinder();
        finder.registerRoute(TestPresenter.class);
        finder.registerRoute(TestCommand.class);

        Route route = finder.findRoute(RequestMethod.POST, "/test", scope);

        assertThat(route, instanceOf(CommandRoute.class));
        assertEquals(TestCommand.class, route.getHandlerType());
        assertEquals(new URITemplate("/test"), route.getTemplate());
    }

    @Test
    public void shouldNotAllowTraceMethod() throws Exception {
        RouteFinder finder = new RouteFinder();
        Route route = finder.findRoute(RequestMethod.TRACE, "/test", null);
        assertEquals(MethodNotAllowedResponse.class, route.getHandlerType());

        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        Response response = route.process(null);
        response.render(null, mockResponse);

        verify(mockResponse).setHeader("Allow", "GET,POST");
        verify(mockResponse).sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }

    @Test
    public void shouldNotAllowPostMethodForGetMapping() throws Exception {
        RouteFinder finder = new RouteFinder();
        finder.registerRoute(TestPresenter.class);

        Route route = finder.findRoute(RequestMethod.POST, "/test", null);
        assertEquals(MethodNotAllowedResponse.class, route.getHandlerType());

        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        Response response = route.process(null);
        response.render(null, mockResponse);

        verify(mockResponse).setHeader("Allow", "GET");
        verify(mockResponse).sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }

    @Test
    public void shouldNotFindRouteForUnknownPath() {
        RouteFinder finder = new RouteFinder();
        Route route = finder.findRoute(RequestMethod.GET, "/foo", null);
        assertEquals(NotFoundResponse.class, route.getHandlerType());
    }

    @RouteMapping("/test")
    private class TestPresenter implements Presenter {
        public Response display(Request request) {
            throw new UnsupportedOperationException();
        }
    }

    @RouteMapping("/test")
    private class TestCommand implements Command {
        public Redirect execute(Request request) {
            throw new UnsupportedOperationException();
        }
    }
}
