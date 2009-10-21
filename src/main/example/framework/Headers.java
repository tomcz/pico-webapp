package example.framework;

import example.utils.Lists;
import example.utils.Maps;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class Headers {

    private final Map<String, List<String>> headers = Maps.create();

    public void addHeader(String name, String value) {
        if (headers.containsKey(name)) {
            headers.get(name).add(value);
        } else {
            setHeader(name, value);
        }
    }

    public void setHeader(String name, String value) {
        headers.put(name, Lists.create(value));
    }

    public void addTo(HttpServletResponse response) {
        for (String name : headers.keySet()) {
            boolean isFirst = true;
            for (String value : headers.get(name)) {
                if (isFirst) {
                    response.setHeader(name, value);
                } else {
                    response.addHeader(name, value);
                }
                isFirst = false;
            }
        }
    }
}
