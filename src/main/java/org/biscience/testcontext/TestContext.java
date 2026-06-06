package org.biscience.testcontext;

import java.util.HashMap;
import java.util.Map;

public class TestContext {

    private final Map<ContextData, Object> data = new HashMap<>();

    @SuppressWarnings("unchecked")
    public <T> T get(ContextData key) {
        return (T) data.get(key);
    }

    public void put(ContextData key, Object value) {
        data.put(key, value);
    }
}