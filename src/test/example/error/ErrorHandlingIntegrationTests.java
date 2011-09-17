package example.error;

import example.TestApplication;
import example.framework.Location;
import example.framework.RequestMethod;
import example.framework.test.TestRequestContext;
import example.framework.test.TestResponseContext;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.text.pattern.PatternMatcher.matchesPattern;
import static org.hamcrest.text.pattern.Patterns.anyCharacterIn;
import static org.hamcrest.text.pattern.Patterns.exactly;
import static org.hamcrest.text.pattern.Patterns.sequence;
import static org.junit.Assert.assertThat;

public class ErrorHandlingIntegrationTests {

    @Test
    public void shouldHandleApplicationErrorsByRedirectingUserToErrorReferencePresenter() throws Exception {
        TestApplication application = new TestApplication();

        Location location = new Location(BadPresener.class);
        TestResponseContext response = application.process(new TestRequestContext(RequestMethod.GET, location));

        String redirectedUrl = response.getRedirectURL();
        assertThat(redirectedUrl, matchesPattern(sequence("/error/", exactly(7, anyCharacterIn("A-Z0-9")))));

        String errorRef = StringUtils.substringAfterLast(redirectedUrl, "/");

        response = application.process(new TestRequestContext(RequestMethod.GET, new Location(redirectedUrl)));

        Document document = Jsoup.parse(response.getResponseBodyText());

        Elements elements = document.select("#errorRef");
        assertThat(elements.size(), equalTo(1));

        assertThat(elements.first().text(), equalTo(errorRef));
    }
}
