package bg.sofia.uni.fmi.mjt.pipeline;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CacheTest {

    private Cache test;

    @BeforeEach
    void setUp() {
        test = new Cache();
    }

    @Test
    void testCacheValueNullKey() {
        assertThrows(IllegalArgumentException.class, () -> test.cacheValue(null, "20"),
                "Cache key must not be null, if it is we throw IllegalArgumentException");
    }

    @Test
    void testCacheValueNullValue() {
        assertThrows(IllegalArgumentException.class, () -> test.cacheValue("20", null),
                "Cache value must not be null, if it is we throw IllegalArgumentException");
    }

    @Test
    void testGetCacheValueNull() {
        assertThrows(IllegalArgumentException.class, () -> test.getCachedValue(null),
                "Cache key must not be null, if it is we throw IllegalArgumentException");
    }

    @Test
    void testCacheContainsKey() {
        assertThrows(IllegalArgumentException.class, () -> test.containsKey(null),
                "Cache key must not be null, if it is we throw IllegalArgumentException");
    }


}