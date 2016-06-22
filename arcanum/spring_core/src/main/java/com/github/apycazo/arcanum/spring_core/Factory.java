
package com.github.apycazo.arcanum.spring_core;

import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Andres Picazo
 */

public class Factory {
    
    @Autowired
    private LoggerBean logger;
    
    public static enum FACTORY_TYPE { pen, ink }
    
    private FACTORY_TYPE type;
    private String name;
    private int counter;
    
    public Factory () {
        this.type = FACTORY_TYPE.pen;
        this.name = "default";
        this.counter = 0;
    }
    
    public Factory setType (FACTORY_TYPE type) {
        this.type = type;
        return this;
    }
    
    public Factory setName (String name) {
        this.name = name;
        return this;
    }
    
    public FACTORY_TYPE getType () {
        return type;
    }
    
    public String getName () {
        return this.name;
    }
    
    public void heartbeat () {
        logger.log("<beat>"+this.name+"<beat>");
    }
    
    public Product build () {
        Product product;
        String assignedId;
        switch (this.type) {
            case pen:
                assignedId = this.name+"-"+this.counter;
                product = new Product(Product.PRODUCT_TYPE.pen, assignedId);
                this.counter++;
                break;
            case ink:
                assignedId = this.name+"-"+this.counter;
                product = new Product(Product.PRODUCT_TYPE.ink, assignedId);
                this.counter++;
                break;
            default:
                product = null;
        }
        return product;
    }
    
}
