package com.example.springsecuritydemo.service;

import com.example.springsecuritydemo.exception.UserNotFoundException;
import com.example.springsecuritydemo.repository.UserRepository;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class LoginService {
    public static final int MAX_ATTEMPT_IP_ADDRESS = 4;
    public static final int MAX_ATTEMPT_LOGIN = 2;
    private final LoadingCache<String, Integer> attemptsIPAddressCache;
    private final Map<String, Integer> attemptsUserMap;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    UserRepository userRepository;

    public LoginService() {
        super();
        attemptsIPAddressCache = CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.MINUTES).
                build(new CacheLoader<String, Integer>() {
            @Override
            public Integer load(final String key) {
                return 0;
            }
        });
        attemptsUserMap = new HashMap<>();
    }

    /**
     * Login thành công reset lại toàn bộ count fail  */
    public void loginSuccess(final String idAddress, String username) throws ExecutionException {
        int attemptsIPAddress = attemptsIPAddressCache.get(idAddress);
        if (attemptsIPAddress != 0 ) {
            attemptsIPAddressCache.put(idAddress, 0);
        }

        if (attemptsUserMap.containsKey(username)) {
            int attemptsUser = attemptsUserMap.get(username);
            if (attemptsUser != 0 ) {
                attemptsUserMap.put(username, 0);
            }
        }

    }

    public void loginFailedIPAddress(String idAddress) {
        System.out.println("loginFailedIPAddress");
        int attempts;
        try {
            attempts = attemptsIPAddressCache.get(idAddress);
        } catch (final ExecutionException e) {
            attempts = 0;
        }
        attempts++;
        attemptsIPAddressCache.put(idAddress, attempts);
    }

    public void loginFailedUser(String username) {
        int attempts;
        try {
            attempts = attemptsUserMap.get(username);
        } catch (NullPointerException e) {
            attempts= 0;
        }
        attempts++;
        System.out.println("loginFailedUser = " + username + " count = " + attempts);
        attemptsUserMap.put(username, attempts);
    }

    public String getClientIP() {
        final String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader != null) {
            return xfHeader.split(",")[0];
        }

        return request.getRemoteAddr();
    }

    public boolean isIPAddressBlocked() {
        try {
            return attemptsIPAddressCache.get(getClientIP()) >= MAX_ATTEMPT_IP_ADDRESS;
        } catch (final ExecutionException e) {
            return false;
        }
    }

    public String getUsername() {
        return request.getParameter("username");
    }

    public boolean isUserBlocked(String username) throws UserNotFoundException {
        Boolean result = false;
        result = attemptsUserMap.get(getUsername()) >= MAX_ATTEMPT_LOGIN;
        return result;
    }


}
