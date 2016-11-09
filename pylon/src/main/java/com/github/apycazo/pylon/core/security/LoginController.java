package com.github.apycazo.pylon.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Andres Picazo
 */
@RestController
@RequestMapping(value = "${basic-security.mapping:}", produces = MediaType.APPLICATION_JSON_VALUE)
@ConditionalOnBean(SecurityHandler.class)
public class LoginController
{
    @Autowired
    private SecurityHandler securityHandler;

    private Map<String,Object> response (String msg)
    {
        Map<String, Object> map = new HashMap<>();
        map.put("msg", msg);
        map.put("ts", System.currentTimeMillis());
        return map;
    }

    @PostMapping("login")
    public ResponseEntity login(HttpServletRequest request)
    {
        String authHeader = securityHandler.getLoginHeader();
        String auth = request.getHeader(authHeader);
        if (StringUtils.isEmpty(auth)) {
            return new ResponseEntity<>(response(authHeader + " not found or invalid"), HttpStatus.FORBIDDEN);
        }

        String token = securityHandler.processLogin(auth);
        if (!StringUtils.isEmpty(token)) {
            UserSession userSession = securityHandler.getSessions().getSession(token);
            return new ResponseEntity<>(userSession, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response("User/Pass unauthorized"), HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("logout")
    public ResponseEntity logout(HttpServletRequest request)
    {
        String tokenHeader = securityHandler.getTokenHeader();
        String token = request.getHeader(tokenHeader);
        if (StringUtils.isEmpty(token)) {
            return new ResponseEntity<>(response(tokenHeader + " not found or invalid"), HttpStatus.FORBIDDEN);
        }
        UserSession userSession = securityHandler.getSessions().getSession(token);
        if (userSession == null) {
            return new ResponseEntity<>(response("Session not found"), HttpStatus.FORBIDDEN);
        }
        else {
            securityHandler.getSessions().removeSession(token);
            return new ResponseEntity(HttpStatus.OK);
        }
    }
}
