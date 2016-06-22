package com.github.apycazo.arcanum.spring_core;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Very simple reference for Spring-Core/beans...
 * Uses a little more advanced version, using a BeanFactory to produce multiple
 * instances of a prototype bean (Factory).
 * 
 * Mixes xml bean definition (worker), with autowired elements (logger).
 * @author Andres Picazo
 */

public class App {

    public static void main(String[] args) {
        System.out.println("Spring Core Demo");
        
        ApplicationContext appContext = new ClassPathXmlApplicationContext("spring-cfg.xml");
        // Get Business Object
        WorkerBean worker = (WorkerBean)appContext.getBean("worker");
        // Check if I actually have the bean ready
        if (worker == null) {
            System.err.println("Error: Bean not found");
            System.exit(0);
        }
        else {
            // Show worker bean properties
            System.out.println("Worker Bean ID: " + worker.getBeanName());
            System.out.println("Worker Name   : " + worker.getWorkerName());
            // Test logic
            worker.work();            
        }        
    }
}
