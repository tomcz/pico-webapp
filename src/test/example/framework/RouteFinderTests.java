package example.framework;

import example.utils.Lists;
import example.utils.Maps;
import example.utils.Pair;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import javax.servlet.http.HttpServletResponse;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class RouteFinderTests {

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToRegisterHandlerTypeWithoutAnnotation() throws Exception {
        RouteFinder finder = new RouteFinder();
        finder.registerRoute(String.class);
    }

    @Test
    public void shouldCreatePresenterRouteForGetRequest() throws Exception {
        Container scope = new PicoContainer();
        scope.registerInstance(new TestPresenter());

        RouteFinder finder = new RouteFinder();
        finder.registerRoute(TestPresenter.class);

        Route route = finder.findRoute(RequestMethod.GET, "/test", scope).getKey();

        assertThat(route, instanceOf(PresenterRoute.class));
        assertRouteInvokes(route, TestPresenter.class);
    }

    @Test
    public void shouldCreateCommandRouteForPostRequest() throws Exception {
        Container scope = new PicoContainer();
        scope.registerInstance(new TestCommand());

        RouteFinder finder = new RouteFinder();
        finder.registerRoute(TestCommand.class);

        Route route = finder.findRoute(RequestMethod.POST, "/test", scope).getKey();

        assertThat(route, instanceOf(CommandRoute.class));
        assertRouteInvokes(route, TestCommand.class);
    }

    @Test
    public void shouldCreatePresenterRouteForGetRequestWhenHaveCommandAndPresenerMappedToSamePath() throws Exception {
        Container scope = new PicoContainer();
        scope.registerInstance(new TestPresenter());

        RouteFinder finder = new RouteFinder();
        finder.registerRoute(TestPresenter.class);
        finder.registerRoute(TestCommand.class);

        Route route = finder.findRoute(RequestMethod.GET, "/test", scope).getKey();

        assertThat(route, instanceOf(PresenterRoute.class));
        assertRouteInvokes(route, TestPresenter.class);
    }

    @Test
    public void shouldCreateCommandRouteForPostRequestWhenHaveCommandAndPresenerMappedToSamePath() throws Exception {
        Container scope = new PicoContainer();
        scope.registerInstance(new TestCommand());

        RouteFinder finder = new RouteFinder();
        finder.registerRoute(TestPresenter.class);
        finder.registerRoute(TestCommand.class);

        Route route = finder.findRoute(RequestMethod.POST, "/test", scope).getKey();

        assertThat(route, instanceOf(CommandRoute.class));
        assertRouteInvokes(route, TestCommand.class);
    }

    @Test
    public void shouldNotAllowTraceMethod() throws Exception {
        RouteFinder finder = new RouteFinder();
        Route route = finder.findRoute(RequestMethod.TRACE, "/test", null).getKey();

        ResponseContext context = mock(ResponseContext.class);
        Response response = route.process(null);
        response.render(context);

        verify(context).sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);

        ArgumentCaptor<Header> captor = ArgumentCaptor.forClass(Header.class);
        verify(context).setHeader(captor.capture());

        Header header = captor.getValue();
        assertThat(header.getFields(), is(Maps.create("Allow", Lists.create("GET,POST"))));
    }

    @Test
    public void shouldNotAllowPostMethodForGetMapping() throws Exception {
        RouteFinder finder = new RouteFinder();
        finder.registerRoute(TestPresenter.class);

        Route route = finder.findRoute(RequestMethod.POST, "/test", null).getKey();

        ResponseContext context = mock(ResponseContext.class);
        Response response = route.process(null);
        response.render(context);

        verify(context).sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);

        ArgumentCaptor<Header> captor = ArgumentCaptor.forClass(Header.class);
        verify(context).setHeader(captor.capture());

        Header header = captor.getValue();
        assertThat(header.getFields(), is(Maps.create("Allow", Lists.create("GET"))));
    }

    @Test
    public void shouldNotFindRouteForUnknownPath() throws Exception {
        RouteFinder finder = new RouteFinder();
        Route route = finder.findRoute(RequestMethod.GET, "/foo", null).getKey();

        ResponseContext context = mock(ResponseContext.class);
        Response response = route.process(null);
        response.render(context);

        verify(context).sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    @Test
    public void shouldCreateRouteWithAccessFilter() {
        Container scope = new PicoContainer();
        scope.registerInstance(new TestPresenter());
        scope.registerInstance(new TestAccessFilter());

        RouteFinder finder = new RouteFinder();
        finder.registerRoute(TestPresenter.class, TestAccessFilter.class);

        Route route = finder.findRoute(RequestMethod.GET, "/test", scope).getKey();

        assertThat(route, instanceOf(AccessFilterRoute.class));
        assertRouteInvokes(route, TestAccessFilter.class);
    }

    @Test
    public void shouldParsePathVariablesFromLookupPath() throws Exception {
        Container scope = new PicoContainer();
        scope.registerInstance(new TestPresenterWithPathVars());

        RouteFinder finder = new RouteFinder();
        finder.registerRoute(TestPresenterWithPathVars.class);

        Pair<Route, PathVariables> mapping = finder.findRoute(RequestMethod.GET, "/test/foo", scope);

        assertRouteInvokes(mapping.getKey(), TestPresenterWithPathVars.class);

        PathVariables vars = mapping.getValue();
        assertThat(vars.get("documentId"), is("foo"));
    }

    private void assertRouteInvokes(Route route, Class handlerType) {
        try {
            route.process(mock(Request.class));
            fail("Should have thrown exception from the test handler");
        } catch (UnsupportedOperationException e) {
            assertThat(e.getMessage(), is(handlerType.getName()));
        }
    }

    @RouteMapping("/test")
    private class TestPresenter implements Presenter {
        public Response display(Request request) {
            throw new UnsupportedOperationException(getClass().getName());
        }
    }

    @RouteMapping("/test")
    private class TestCommand implements Command {
        public Redirect execute(Request request) {
            throw new UnsupportedOperationException(getClass().getName());
        }
    }

    @RouteMapping("/test/{documentId}")
    private class TestPresenterWithPathVars implements Presenter {
        public Response display(Request request) {
            throw new UnsupportedOperationException(getClass().getName());
        }
    }

    private class TestAccessFilter implements AccessFilter {
        public Response check(Request request) {
            throw new UnsupportedOperationException(getClass().getName());
        }
    }
}
