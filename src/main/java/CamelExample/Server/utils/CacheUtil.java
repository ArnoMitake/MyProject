package CamelExample.Server.utils;


import org.springframework.cache.annotation.Cacheable;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class CacheUtil {

    @Cacheable(value = "Cache_Test", key = "#a", sync = true)
    public List<String> getTest(String a) {
        return Arrays.asList(getDBdata(a));
    }
    
    public String getDBdata(String a) {
        System.out.println(a.getBytes(StandardCharsets.UTF_8).length);
        return a;
    }
}

