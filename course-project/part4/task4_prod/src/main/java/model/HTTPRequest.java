package model;

import java.util.Map;
import java.util.StringTokenizer;

public class HTTPRequest {
    private HTTPRequestLine requestLine;
    private Map<String,String> headers;
    private Map<String,String> parameters;
    private StringTokenizer body;

    public void setBody(StringTokenizer body) {
        this.body = body;
    }

    public void setRequestLine(HTTPRequestLine requestLine) {
        this.requestLine = requestLine;
    }

    public boolean hasHeader(String key){
        return headers.containsKey(key);
    }

    public HTTPRequestLine getRequestLine() {
        return requestLine;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }
}
