package com.github.apycazo.pylon.core.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Andres Picazo
 */
@Slf4j
@Component
@ConditionalOnBean(SecurityHandler.class)
public class SessionStore
{
    private Map<String, UserSession> sessions = new ConcurrentHashMap<>();
    private int maxSessionIdleTimeInMillis = 300_000;

    public void setMaxSessionIdleTime(int seconds)
    {
        maxSessionIdleTimeInMillis = seconds * 1000;
    }

    public synchronized void storeSession(UserSession userSession)
    {
        sessions.put(userSession.getToken(), userSession);
    }

    public synchronized void removeSession(String token)
    {
        sessions.remove(token);
    }

    public UserSession getSession(String token)
    {
        // Retrieves session
        UserSession userSession = sessions.get(token);
        if (userSession != null) {
            // Update timestamp (session is not idle)
            userSession.setTimestamp(System.currentTimeMillis());
        }
        return userSession;
    }

    @Scheduled(fixedDelay = 10000)
    public synchronized void cleanupSessions()
    {
        int currentSessions = sessions.size();
        long maxTs = System.currentTimeMillis() - maxSessionIdleTimeInMillis;
        sessions.entrySet().removeIf(e -> maxTs > e.getValue().getTimestamp());
        if (sessions.size() < currentSessions) {
            log.info("Expired {} sessions", currentSessions - sessions.size());
        }
    }
}
