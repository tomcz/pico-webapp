package example.domain.web.nodriver;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import example.framework.Location;
import example.framework.RequestMethod;
import example.framework.test.TestRequestContext;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

@SuppressWarnings({"unchecked"})
public class HtmlForm {

    private final Element form;
    private final Browser browser;

    public HtmlForm(Element form, Browser browser) {
        this.browser = browser;
        this.form = form;
    }

    public void setInputValue(String fieldName, String value) {
        input(fieldName).val(value);
    }

    public void showsInputValue(String fieldName, String value) {
        assertThat(fieldName, input(fieldName).val(), equalTo(value));
    }

    public void selectOptionByValue(String fieldName, String value) {
        for (Element option : options(fieldName, "option")) {
            if (option.val().equals(value)) {
                option.attr("selected", "selected");
            } else {
                option.removeAttr("selected");
            }
        }
    }

    public void showsSelectedOptionWithValue(String fieldName, String value) {
        Elements options = options(fieldName, "option[selected]");
        if (!elementWithValue(options, value).isPresent()) {
            fail("Select '" + fieldName + "' does not have '" + value + "' selected in " + form.outerHtml());
        }
    }

    private Optional<Element> elementWithValue(Elements elements, final String value) {
        return FluentIterable.from(elements).firstMatch(new Predicate<Element>() {
            public boolean apply(Element element) {
                return element.val().equals(value);
            }
        });
    }

    public <T> T submitAndExpect(Class<T> pageClass) {
        String methodStr = StringUtils.defaultIfEmpty(form.attr("method"), "post");
        String actionStr = StringUtils.defaultIfEmpty(form.attr("action"), browser.currentURI());

        RequestMethod method = RequestMethod.valueOf(methodStr.toUpperCase());
        Location location = new Location(actionStr);

        TestRequestContext request = new TestRequestContext(method, location);
        addTextFieldValues(request);
        addSelectedOptions(request);

        return browser.send(request, pageClass);
    }

    private void addTextFieldValues(TestRequestContext request) {
        for (Element input : form.select("input[type=text]")) {
            request.addParameter(input.attr("name"), input.val());
        }
    }

    private void addSelectedOptions(TestRequestContext request) {
        for (Element select : form.select("select")) {
            String name = select.attr("name");
            Elements selected = select.select("option[selected]");
            if (selected.isEmpty()) {
                Elements options = select.select("option");
                if (options.size() > 0) {
                    request.addParameter(name, options.first().val());
                }
            } else {
                for (Element option : selected) {
                    request.addParameter(name, option.val());
                }
            }
        }
    }

    private Element input(String fieldName) {
        return first("input[name=" + fieldName + "][type=text]");
    }

    private Elements options(String fieldName, String option) {
        String query = "select[name=" + fieldName + "] " + option;
        Elements options = form.select(query);
        if (options.isEmpty()) {
            fail("Cannot find " + query + " in " + form.outerHtml());
        }
        return options;
    }

    private Element first(String selector) {
        Elements elements = form.select(selector);
        if (elements.isEmpty()) {
            fail("Cannot find " + selector + " in " + form.outerHtml());
        }
        return elements.first();
    }
}
