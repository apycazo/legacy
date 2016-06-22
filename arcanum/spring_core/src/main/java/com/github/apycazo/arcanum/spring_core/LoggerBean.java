
package com.github.apycazo.arcanum.spring_core;

import org.springframework.stereotype.Component;

/**
 *
 * @author Andres Picazo
 */

@Component("logger")
public class LoggerBean {
    
    public void log(String message) {
        System.out.println("[Logger] " + message);
    }
    
}
