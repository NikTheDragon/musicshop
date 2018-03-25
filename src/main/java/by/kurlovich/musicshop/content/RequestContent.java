package by.kurlovich.musicshop.content;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class RequestContent {
    private HttpServletRequest request;
    private Map<String, String> requestParameters = new HashMap<>();

    public void setRequestParameters(String key, String value) {
        requestParameters.put(key, value);
    }

    public String getRequestParameter(String key) {
        return requestParameters.get(key);
    }

    public Map<String, String> getRequestMap() {
        return requestParameters;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletRequest getRequest() {
        return request;
    }
}
