package com.github.apycazo.voodoo.rest.service;

import com.github.apycazo.voodoo.core.StringTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Andres Picazo
 */
@Component("voodoo-rest:http-trace-filter")
@ConditionalOnProperty(prefix = "voodoo.rest.components.filter", name = "enable", matchIfMissing = false)
public class HttpTraceFilter extends OncePerRequestFilter implements Ordered
{
    protected Logger log = LoggerFactory.getLogger(HttpTraceFilter.class);

    // Not to interfere with spring actuator tracer
    protected int order = Ordered.LOWEST_PRECEDENCE - 20;

    @Override
    protected void doFilterInternal(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            FilterChain filterChain)
            throws ServletException, IOException
    {

        StringBuilder sb = new StringBuilder();
        String query = StringTools.nullSafe(httpServletRequest.getQueryString());
        String trace = sb
                .append("Request ")
                .append(httpServletRequest.getMethod())
                .append(" on ")
                .append(httpServletRequest.getRequestURI())
                .append(query.isEmpty() ? "" : "?" + query)
                .append(" from ")
                .append(httpServletRequest.getRemoteHost())
                .toString();

        log.debug(trace);
        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }

    @Override
    public int getOrder()
    {
        return this.order;
    }

    public void setOrder(int order)
    {
        this.order = order;
    }
}
