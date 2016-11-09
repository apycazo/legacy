package com.github.apycazo.pylon.core.security;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.keygen.StringKeyGenerator;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * @author Andres Picazo
 */
@Slf4j
@ConfigurationProperties(prefix = "basic-security")
public abstract class SecurityHandler extends WebMvcConfigurerAdapter
{
    @Autowired
    @Getter
    protected SessionStore sessions;
    @Getter @Setter
    private String loginHeader = "X-Login";
    @Getter @Setter
    private String tokenHeader = "X-Token";
    @Getter @Setter
    protected int sessionIdleTimeInSeconds = 300;
    /**
     * By default, login must be done from the login endpoint. If true, any endpoint can login.
     */
    @Getter @Setter
    private boolean loginRestricted = true;
    @Getter
    private List<SecurityPolicy> policies = new ArrayList<>();
    private final AntPathMatcher matcher = new AntPathMatcher();
    private StringKeyGenerator keygen;

    @PostConstruct
    protected void init()
    {
        configurePolicies(policies);
        sessions.setMaxSessionIdleTime(getSessionIdleTimeInSeconds());
        keygen = KeyGenerators.string();
        log.info("{} loaded", getClass().getSimpleName());
    }

    protected abstract void configurePolicies(List<SecurityPolicy> policies);

    protected abstract boolean login(UserSession userSession, String password);

    protected int getSessionIdleTimeInSeconds()
    {
        return sessionIdleTimeInSeconds;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(new AuthInterceptor(this));
    }

    public SecurityPolicy getPolicyForURI(String URI)
    {
        if (policies == null || policies.isEmpty()) {
            return null;
        } else {
            Iterator<SecurityPolicy> it = policies.listIterator();
            SecurityPolicy policy = null;
            while (policy == null && it.hasNext()) {
                SecurityPolicy next = it.next();
                String pattern = Optional.ofNullable(next.getPattern()).orElse("");
                if (matcher.match(pattern, URI)) {
                    policy = next;
                }
            }
            return policy;
        }
    }

    public String processLogin(String value)
    {
        StringTokenizer st = new StringTokenizer(value, ":");
        if (st.countTokens() != 2) {
            return null;
        } else {
            String user = st.nextToken().trim();
            String pass = st.nextToken().trim();
            UserSession userSession = UserSession.builder()
                    .username(user)
                    .timestamp(System.currentTimeMillis())
                    .token(keygen.generateKey())
                    .build();
            if (login(userSession, pass)) {
                sessions.storeSession(userSession);
                return userSession.getToken();
            } else {
                return "";
            }
        }
    }

    public boolean processLogout(String token)
    {
        if (processToken(token)) {
            sessions.removeSession(token);
            return true;
        } else return false;
    }

    public boolean processToken(String token)
    {
        return sessions.getSession(token) != null;
    }

    public boolean hasValidRole (SecurityPolicy policy, String token)
    {
        UserSession userSession = sessions.getSession(token);

        if (userSession == null) {
            // no session!
            return false;
        }

        List<String> roles = userSession.getRoles();

        if (!policy.requiresRole()) {
            return true;
        } else {
            // role required, but none has been found
            return !(roles == null || roles.isEmpty()) && policy.allowsRoleToProceed(roles);
        }
    }

}
