package example.framework;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class URITemplateFactory {

    private static final Map<String, URITemplate> CACHE = new ConcurrentHashMap<String, URITemplate>();

    public static URITemplate createFrom(String path) {
        URITemplate template = CACHE.get(path);
        if (template == null) {
            template = new URITemplate(path);
            CACHE.put(path, template);
        }
        return template;
    }

    @SuppressWarnings({"unchecked"})
    public static URITemplate createFrom(Class handlerType) {
        if (handlerType.isAnnotationPresent(RouteMapping.class)) {
            RouteMapping mapping = (RouteMapping) handlerType.getAnnotation(RouteMapping.class);
            return createFrom(mapping.value());
        }
        throw new IllegalArgumentException("RouteMapping annotation not found on " + handlerType);
    }
}
