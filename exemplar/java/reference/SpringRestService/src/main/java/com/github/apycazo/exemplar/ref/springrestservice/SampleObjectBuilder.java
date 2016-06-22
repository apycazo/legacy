

package com.github.apycazo.exemplar.ref.springrestservice;

import org.springframework.stereotype.Component;

@Component("sampleBuilder")
public class SampleObjectBuilder {
    
    public String identifier;
    
    public SampleObjectBuilder () {
        this.identifier = "singleton";
    }

    public SimpleRegister createDefaultRegister () {
        return new SimpleRegister();
    }

    public SimpleRegister createSecuredRegister () {
        SimpleRegister reg = new SimpleRegister();
        reg.id = "secured " + reg.id;
        return reg;
    }
    
}
