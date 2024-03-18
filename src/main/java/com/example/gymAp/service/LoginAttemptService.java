package com.example.gymAp.service;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginAttemptService {

    private static final int MAX_ATTEMPTS = 3;
    private static final long BLOCK_DURATION = 10 * 1000; // 5 minutes in milliseconds

    private final Map<String, Integer> attemptsCache = new HashMap<>();
    private final Map<String, Long> blockList = new HashMap<>();

    public void loginFailed(String username) {
        attemptsCache.put(username, attemptsCache.getOrDefault(username, 0) + 1);
        if (attemptsCache.getOrDefault(username, 0) >= MAX_ATTEMPTS) {
            blockList.put(username, System.currentTimeMillis() + BLOCK_DURATION);
        }
    }

    public boolean isBlocked(String username) {
        return blockList.containsKey(username) && blockList.get(username) > System.currentTimeMillis();
    }

    public void loginSucceeded(String username) {
        attemptsCache.remove(username);
        blockList.remove(username);
    }
}
