package server.proactor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionState {

    private Map<String, Object> sessionProps = new ConcurrentHashMap<String, Object>();

    public Object getProperty(String key) {
        return sessionProps.get(key);
    }

    public void setProperty(String key, Object value) {
        sessionProps.put(key, value);
    }
}