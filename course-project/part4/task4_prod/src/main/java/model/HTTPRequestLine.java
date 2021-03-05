package model;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class HTTPRequestLine {
    private final String method;
    private final String version;
    private final String path;
    private final Map<String, String> queryParameters;

    public HTTPRequestLine(String method, String version, Map<String, String> queryParameters, String path) {
        this.method = method;
        this.version = version;
        this.path = path;
        this.queryParameters = queryParameters;
    }

    public String getVersion() {
        return version;
    }

    public String getMethod() {
        return method;
    }

    public Map<String, String> getQueryParameters() {
        return queryParameters;
    }

    public String getPath() {
        return path;
    }

    private Map<String, String> decodeQueryString(String query) throws UnsupportedEncodingException {
        Map<String, String> params = new LinkedHashMap<>();
        if(Objects.isNull(query))
            return params;

        for (String param : query.split("&")) {
            String[] keyValue = param.split("=", 2);
            String key = URLDecoder.decode(keyValue[0], "UTF-8");
            String value = keyValue.length > 1 ? URLDecoder.decode(keyValue[1], "UTF-8") : "";
            if (!key.isEmpty()) {
                params.put(key, value);
            }
        }
        return params;
    }
}