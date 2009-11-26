package example.framework;

import example.utils.Lists;
import example.utils.Pair;
import example.utils.Strings;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URITemplate {

    private static final Pattern TOKENS = Pattern.compile("\\{([^{}]+)\\}");
    private static final String TOKENS_REPLACE = "([^/#?]*)?";

    private static final Pattern REGEX_CHARS = Pattern.compile("[\\.\\\\\\[\\]\\(\\)\\&\\^\\$\\?\\#\\+\\*\\|\\>\\<]");
    private static final String REGEX_CHARS_REPLACE = "\\\\$0";

    private final String path;
    private final Pattern pattern;
    private final List<Pair<String, String>> names;

    public URITemplate(String path) {
        this.path = path;

        Matcher matcher = TOKENS.matcher(path);
        this.names = Lists.create();
        while (matcher.find()) {
            String name = matcher.group(1);
            this.names.add(Pair.create(name, "{" + name + "}"));
        }

        String escapedPattern = REGEX_CHARS.matcher(path).replaceAll(REGEX_CHARS_REPLACE);
        escapedPattern = TOKENS.matcher(escapedPattern).replaceAll(TOKENS_REPLACE);
        this.pattern = Pattern.compile(escapedPattern);
    }

    public boolean matches(String uri) {
        return pattern.matcher(uri).matches();
    }

    public PathVariables parse(String uri) {
        PathVariables vars = new PathVariables();
        Matcher matcher = pattern.matcher(uri);
        if (matcher.matches()) {
            int index = 1;
            for (Pair<String, String> pair : names) {
                String value = matcher.group(index++);
                vars.set(pair.getKey(), Strings.decodeURL(value));
            }
        }
        return vars;
    }

    public String expand(PathVariables parameters) {
        String result = getPath();
        for (Pair<String, String> pair : names) {
            String value = parameters.get(pair.getKey());
            if (StringUtils.isEmpty(value)) {
                throw new IllegalArgumentException("Missing value for '" + pair.getValue() + "'");
            }
            result = StringUtils.replace(result, pair.getValue(), Strings.encodeURL(value));
        }
        return result;
    }

    public String getPath() {
        return path;
    }

    public int hashCode() {
        return path.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof URITemplate) {
            URITemplate other = (URITemplate) obj;
            return this.getPath().equals(other.getPath());
        }
        return false;
    }

    public String toString() {
        return getPath();
    }
}
