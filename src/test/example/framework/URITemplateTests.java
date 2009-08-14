package example.framework;

import example.utils.Maps;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Test;

import java.util.Map;

public class URITemplateTests {

    @Test
    public void shouldMatchTemplateWithToken() throws Exception {
        URITemplate template = new URITemplate("/servlet/{type}/view.do");
        assertThat("Should match pattern", template.matches("/servlet/images/view.do"), equalTo(true));
    }

    @Test
    public void shouldParseTemplateWithToken() throws Exception {
        URITemplate template = new URITemplate("/servlet/{type}/view.do");
        Map<String, String> parsed = template.parse("/servlet/images/view.do");
        assertThat(parsed.size(), equalTo(1));
        assertThat(parsed.get("type"), equalTo("images"));
    }

    @Test
    public void shouldMatchTemplateWithoutTokens() throws Exception {
        URITemplate template = new URITemplate("/servlet/images/view.do");
        assertThat("Should match pattern", template.matches("/servlet/images/view.do"), equalTo(true));
    }

    @Test
    public void shouldParseTemplateWithoutTokens() throws Exception {
        URITemplate template = new URITemplate("/servlet/images/view.do");
        assertThat(template.parse("/servlet/images/view.do").size(), equalTo(0));
    }

    @Test
    public void shouldNotMatchInvalidURI() throws Exception {
        URITemplate template = new URITemplate("/servlet/{type}/view.do");
        assertThat("Should not match pattern", template.matches("/servlet/images/edit.do"), equalTo(false));
    }

    @Test
    public void shouldNotParseInvalidURI() throws Exception {
        URITemplate template = new URITemplate("/servlet/{type}/view.do");
        assertThat(template.parse("/servlet/images/edit.do").size(), equalTo(0));
    }

    @Test
    public void shouldMatchTemplateWithMutlipleTokens() throws Exception {
        URITemplate template = new URITemplate("/servlet/{type}/{reference}/view.do");
        assertThat("Should match pattern", template.matches("/servlet/images/33kk98vjoessgf3/view.do"), equalTo(true));
    }

    @Test
    public void shouldParseTemplateWithMultipleTokens() throws Exception {
        URITemplate template = new URITemplate("/servlet/{type}/{reference}/view.do");
        Map<String, String> parsed = template.parse("/servlet/images/33kk98vjoessgf3/view.do");
        assertThat(parsed.size(), equalTo(2));
        assertThat(parsed.get("type"), equalTo("images"));
        assertThat(parsed.get("reference"), equalTo("33kk98vjoessgf3"));
    }

    @Test
    public void shouldExpandTemplateWithToken() throws Exception {
        URITemplate template = new URITemplate("/servlet/{type}/view.do");
        Map<String, String> parameters = Maps.create("type", "images");
        assertThat(template.expand(parameters), equalTo("/servlet/images/view.do"));
    }

    @Test
    public void shouldExpandTemplateWithMutlipleTokens() throws Exception {
        URITemplate template = new URITemplate("/servlet/{type}/{reference}/view.do");

        Map<String, String> parameters = Maps.create();
        parameters.put("type", "images");
        parameters.put("reference", "33kk98vjoessgf3");

        assertThat(template.expand(parameters), equalTo("/servlet/images/33kk98vjoessgf3/view.do"));
    }

    @Test
    public void shouldExpandTemplateWithoutTokens() throws Exception {
        URITemplate template = new URITemplate("/servlet/images/view.do");
        assertThat(template.expand(Maps.<String, String>create()), equalTo("/servlet/images/view.do"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotExpandWhenParameterIsMissing() throws Exception {
        URITemplate template = new URITemplate("/servlet/{type}/{reference}/view.do");
        Map<String, String> parameters = Maps.create("type", "images");
        template.expand(parameters);
    }
}
