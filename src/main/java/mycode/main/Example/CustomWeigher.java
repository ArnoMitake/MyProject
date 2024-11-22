package mycode.main.Example;

import com.github.benmanes.caffeine.cache.Weigher;

import java.util.List;

public class CustomWeigher implements Weigher<String, List<String>> {
    @Override
    public int weigh(String key, List<String> value) {
        System.out.println(value.size());
        // Example weight calculation based on the length of the value
        return value.size();
    }
}
