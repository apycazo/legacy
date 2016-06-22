package com.github.apycazo.hi5.shared.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @autor Andres Picazo
 */
@Slf4j
@Order(1)
public class OriginFilter implements Filter {

    protected String allowedOrigins;

    public OriginFilter () {

        this("*");
    }

    public OriginFilter (String allowedOrigins) {

        log.debug("Setting allowed origins header to: {}", allowedOrigins);
        this.allowedOrigins = allowedOrigins;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain
    ) throws IOException, ServletException {

        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", allowedOrigins);
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));

        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) {
        // Empty method
    }

    @Override
    public void destroy() {
        // Empty method
    }
}
