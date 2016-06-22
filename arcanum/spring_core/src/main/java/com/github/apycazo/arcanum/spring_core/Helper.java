

package com.github.apycazo.arcanum.spring_core;

import org.springframework.stereotype.Component;

/**
 * Helper bean to give to Worker.
 * @author Andres Picazo
 */

@Component
public class Helper {
    
    public Product build (Factory factory) {
        return factory.build();
    }
    
}
