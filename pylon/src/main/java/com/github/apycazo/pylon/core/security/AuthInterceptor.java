package com.github.apycazo.pylon.core.security;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * @author Andres Picazo
 */
public class AuthInterceptor implements HandlerInterceptor
{
    private final SecurityHandler securityHandler;

    public AuthInterceptor(SecurityHandler securityHandler)
    {
        this.securityHandler = securityHandler;
    }

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception
    {
        // Check if current endpoint requires some policy to be applied
        SecurityPolicy policy = securityHandler.getPolicyForURI(httpServletRequest.getRequestURI());
        if (policy != null) {
            // check for token headers
            String loginHeader = Optional.ofNullable(httpServletRequest.getHeader(securityHandler.getLoginHeader())).orElse("");
            String tokenHeader = Optional.ofNullable(httpServletRequest.getHeader(securityHandler.getTokenHeader())).orElse("");
            boolean isAuthorized = false;
            // There is an auth token present, process it
            if (!tokenHeader.isEmpty()) {
                isAuthorized = securityHandler.processToken(tokenHeader) && securityHandler.hasValidRole(policy, tokenHeader);
            } else if (!loginHeader.isEmpty() && !securityHandler.isLoginRestricted()) {
                // Auth token not found, but a login one is, try to login.
                String token = securityHandler.processLogin(loginHeader);
                // TODO: Check for valid role!
                if (!StringUtils.isEmpty(token)) {
                    httpServletResponse.setHeader(securityHandler.getTokenHeader(), token);
                    isAuthorized = true;
                }
            }
            if (!isAuthorized) {
                // On error, reject request and set status to 'UNAUTHORIZED'.
                httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
            return isAuthorized;
        }
        else {
            // no policy required, continue
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception
    {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception
    {

    }
}
