package src.model;

import java.util.HashMap;

public class HTTPRequestLine {
    private String method = "";
    private String httpVersion = "";
    private HashMap<String,String> requestURI = new HashMap<>();
    private HashMap<String,String> headers = new HashMap<>();
    private String body;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setHttpVersion(String httpVersion) {
        this.httpVersion = httpVersion;
    }

    private String getURIVar(String name){
        return requestURI.get(name);
    }

    private void addURIVar(String name,String value){
        requestURI.put(name, value);
    }

}
