

package com.github.apycazo.arcanum.spring_core;

import junit.framework.TestCase;

/**
 *
 * @author Andres Picazo
 */
public class FactoryTest extends TestCase {
    
    public FactoryTest(String testName) {
        super(testName);
    }    

    /**
     * Test of setType method, of class Factory.
     */
    public void testSetType() {
        System.out.println("setType");
        Factory.FACTORY_TYPE type = Factory.FACTORY_TYPE.ink;
        Factory instance = new Factory();
        instance.setType(type);
        assertEquals(type, instance.getType());
    }

    /**
     * Test of setName method, of class Factory.
     */
    public void testSetName() {
        System.out.println("setName");
        String name = "test";
        Factory instance = new Factory();
        instance.setName(name);
        assertEquals(name, instance.getName());
    }

    /**
     * Test of build method, of class Factory.
     */
    public void testBuild() {
        System.out.println("build");
        Factory instance = new Factory();
        Product product;
        // Ink factory
        instance.setType(Factory.FACTORY_TYPE.ink);
        product = instance.build();
        assertEquals (Product.PRODUCT_TYPE.ink, product.getType());
        
        // Ink factory
        instance.setType(Factory.FACTORY_TYPE.pen);
        product = instance.build();
        assertEquals (Product.PRODUCT_TYPE.pen, product.getType());
    }
    
}
