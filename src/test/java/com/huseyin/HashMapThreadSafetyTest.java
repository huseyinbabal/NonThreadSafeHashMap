package com.huseyin;

import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import org.junit.Test;

/**
 * This is a simulation of "HashMap is not thread safe".
 * There is so called user database with HashMap and
 * trying to add users concurrently. At a point, same ids
 * are inserted at the same time that causes key overwriting.
 * This results in threadCount != userId size.
 */
public class HashMapThreadSafetyTest {

    /**
     * So called user database
     */
    class Users {
        final Map<Integer, String> users = new HashMap<>();
        int add(String username) {
            final int nextAvailableId = this.users.size() + 1;
            this.users.put(nextAvailableId, username);
            return nextAvailableId;
        }
    }

    @Test
    public void shouldNotBeThreadSafeWhenHashMapProvided() {
        int threadCount = 1000;
        Users users = new Users();
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        List<Future<Integer>> futures = new ArrayList<>(threadCount);
        for (int i = 0; i < threadCount; i++) {
            int userId = i;
            futures.add(executorService.submit(() -> users.add(String.format("User-%s", userId))));
        }

        Set<Integer> userIds = futures.stream().map(future -> {
            try {
                return future.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException();
            }
        }).collect(Collectors.toSet());

        assertNotEquals(threadCount, userIds.size());
    }
}