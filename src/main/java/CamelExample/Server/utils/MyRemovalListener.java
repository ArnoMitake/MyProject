package CamelExample.Server.utils;

import com.github.benmanes.caffeine.cache.RemovalCause;
import com.github.benmanes.caffeine.cache.RemovalListener;

import java.util.List;

public class MyRemovalListener implements RemovalListener<String, List<String>> {
    @Override
    public void onRemoval(String key, List<String> value, RemovalCause cause) {
        System.out.printf("Removed key: %s, value: %s, cause: %s%n", key, value, cause);
    }
}
