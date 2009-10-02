package example.framework;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

import java.util.Arrays;

public class AccessFilterRouteTests {

    @Test
    public void shouldNotInvokeDelegateWhenAccessFilterRejectsRequest() {
        Route delegate = mock(Route.class);

        AccessFilter filter1 = mock(AccessFilter.class);
        AccessFilter filter2 = mock(AccessFilter.class);

        Request request = mock(Request.class);
        Response response = mock(Response.class);

        given(filter2.check(request)).willReturn(response);

        AccessFilterRoute route = new AccessFilterRoute(delegate, Arrays.asList(filter1, filter2));
        assertThat(route.process(request), equalTo(response));

        verify(filter1).check(request);
        verifyZeroInteractions(delegate);
    }

    @Test
    public void shouldInvokeDelegateWhenAccessFiltersAcceptRequest() {
        Route delegate = mock(Route.class);

        AccessFilter filter1 = mock(AccessFilter.class);
        AccessFilter filter2 = mock(AccessFilter.class);

        Request request = mock(Request.class);
        Response response = mock(Response.class);

        given(delegate.process(request)).willReturn(response);

        AccessFilterRoute route = new AccessFilterRoute(delegate, Arrays.asList(filter1, filter2));
        assertThat(route.process(request), equalTo(response));

        verify(filter1).check(request);
        verify(filter2).check(request);
    }
}
