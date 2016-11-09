package com.github.apycazo.pylon.core;

import com.github.apycazo.pylon.core.security.SecurityHandler;
import com.github.apycazo.pylon.core.security.SecurityPolicy;
import com.github.apycazo.pylon.core.security.UserSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class AuthTest extends SecurityHandler
{
    @Override
    protected int getSessionIdleTimeInSeconds()
    {
        return 10;
    }

    @Override
    protected void configurePolicies(List<SecurityPolicy> policies)
    {
        policies.add(new SecurityPolicy("/**/secured/**"));
    }

    @Override
    protected boolean login(UserSession userSession, String password)
    {
        log.info("Login {} password {}", userSession, password);
        return true;
    }
}
