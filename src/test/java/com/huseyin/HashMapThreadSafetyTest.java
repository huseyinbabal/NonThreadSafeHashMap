package com.huseyin;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class HashMapThreadSafetyTest {

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test(timeout = 10000)
    public void shouldNotBeThreadSafeWhenHashMapProvided() {
        expectedEx.expect(RuntimeException.class);
        expectedEx.expectMessage("Reference item is missing.");

        final Map<Integer, String> map = new HashMap<>();

        final int refKey = 10000;
        final String refValue = "value1";
        map.put(refKey, refValue);

        new Thread(() -> IntStream.range(0, refKey).forEach(key -> map.put(key, "value2"))).start();

        int count = 1;
        while (true) {
            String currentValue = map.get(refKey);
            if (!refValue.equals(currentValue)) {
                System.out.println(String.format("Iteration: %s, Ref Value: %s, Updated Value: %s", count, refValue, currentValue));
                throw new RuntimeException("Reference item is missing.");
            }
            count++;
        }
    }
}