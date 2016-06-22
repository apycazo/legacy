
package com.github.apycazo.exemplar.ref.springrestservice;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

/**
 *
 * @author Andres Picazo
 */

// TODO: Actually check for password!!
@Component(value = "BasicFilter")
public class BasicSecurityFilter implements Filter {
    
    public static final String AUTH_HEADER = "AUTH-LOGIN";

    @Override
    public void init(FilterConfig fc) throws ServletException {
        System.out.println("Initializing filter");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("Filtering request");
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        if (authenticate(httpRequest)) {
            chain.doFilter(request, response);
        }
        else {
            httpResponse.getWriter().write("AUTH-LOGIN header required");
            httpResponse.setStatus(401);
        }
    }

    @Override
    public void destroy() {
        System.out.println("Destroying filter");
    }
    
    private boolean authenticate (HttpServletRequest httpRequest) {
        String authValue;
        if ((authValue = httpRequest.getHeader(AUTH_HEADER)) != null) {
            System.out.println("has header!! : " + authValue);
            return true;
        }
        else return false;
    }

    
}
