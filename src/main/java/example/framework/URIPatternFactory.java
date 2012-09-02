package example.framework;

import com.google.common.collect.Maps;
import org.weborganic.furi.URIPattern;

import java.util.Map;

public class URIPatternFactory {

    private static final Map<String, URIPattern> cache = Maps.newConcurrentMap();

    public static URIPattern create(Class<?> handler) {
        RouteMapping mapping = handler.getAnnotation(RouteMapping.class);
        if (mapping == null) {
            throw new IllegalArgumentException("Cannot find RouteMapping annotation on " + handler.getName());
        }
        String template = mapping.value();
        if (cache.containsKey(template)) {
            return cache.get(template);
        }
        URIPattern pattern = new URIPattern(template);
        cache.put(template, pattern);
        return pattern;
    }

    public static String expand(Class<?> handler, PathVariables pathVariables) {
        return create(handler).expand(pathVariables.getParameters());
    }
}
