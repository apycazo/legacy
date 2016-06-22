
package com.github.apycazo.arcanum.spring_core;

/**
 *
 * @author Andres Picazo
 */

public class Product {
    
    public static enum PRODUCT_TYPE { pen, ink }
    
    private final PRODUCT_TYPE type;
    private final String id;
    
    public Product (PRODUCT_TYPE type, String id) {
        this.type = type;
        this.id = id;
    }
    
    public PRODUCT_TYPE getType () {
        return this.type;
    }
    
    public String getId () {
        return this.id;
    }
    
    @Override
    public String toString () {
        StringBuilder sb = new StringBuilder();
        sb.append("Product: ").append(type.name()).append(" ,id:").append(id);
        return sb.toString();
    }
    
}
