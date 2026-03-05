package bg.sofia.uni.fmi.mjt.pipeline;

import java.util.HashMap;
import java.util.Map;

public class Cache {

    private final Map<Object, Object> cache;


    public Cache() {
        cache = new HashMap<>();
    }

    public void cacheValue(Object key, Object value) {
        if(key == null || value == null) {
            throw new IllegalArgumentException("Key or value cannot be null!");
        }
        cache.put(key, value);
    }

    public Object getCachedValue(Object key) {
        if(key == null) {
            throw new IllegalArgumentException("Key cannot be null!");
        }
        return cache.get(key);
    }

    public boolean containsKey(Object key) {
        if(key == null) {
            throw new IllegalArgumentException("Key cannot be null!");
        }
        return cache.containsKey(key);
    }

    public void clear() {
        cache.clear();
    }

    public boolean isEmpty() {
        return cache.isEmpty();
    }
}
