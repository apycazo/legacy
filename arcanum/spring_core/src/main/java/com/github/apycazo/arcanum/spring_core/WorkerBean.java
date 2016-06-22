package com.github.apycazo.arcanum.spring_core;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Andres Picazo
 */

public class WorkerBean implements BeanNameAware {

    @Autowired
    private LoggerBean logger;

    @Autowired
    private ObjectFactory<Factory> beanFactory;
    
    private Factory inkFactory, penFactory;
    private String workerName,beanName;
    private Helper helper;
    
    public WorkerBean () {
        helper = null;
    }
    
    public WorkerBean (Helper helper) {
        this.helper = helper;
    }
    
    private void init () {
        if (inkFactory == null) {
            inkFactory = beanFactory.getObject();
            inkFactory.setName("Ink4U");
            inkFactory.setType(Factory.FACTORY_TYPE.ink);
        }
        if (penFactory == null) {
            penFactory = beanFactory.getObject();
            penFactory.setName("AllAboutWriting");
            penFactory.setType(Factory.FACTORY_TYPE.pen);
        }
    }
    
    public WorkerBean setWorkerName (String name) {
        this.workerName = name;
        return this;
    }
    
    public String getWorkerName () {
        return this.workerName;
    }

    private void task_1() {
        logger.log("Task 1");
        Product product = inkFactory.build();
        logger.log("Created product: " + product.toString());
    }

    
    // This task is done by the helper, if any
    private void task_2() {
        logger.log("Task 2");
        
        if (helper != null) {
            Product product = helper.build(penFactory);
            logger.log("Helper created product: " + product.toString());
        }
        else {
            Product product = penFactory.build();
            logger.log("Created product: " + product.toString());
        }        
    }

    public void work() {
        logger.log("Getting factories");
        init();
        inkFactory.heartbeat();
        penFactory.heartbeat();
        int products2make = 3;
        for (int i = 0; i < products2make; i++) {
            logger.log("Starting work...");
            task_1();
            task_2();
            logger.log("Job done.");
        }
        logger.log("* Task completed *");
    }

    public void setBeanName(String name) {
        this.beanName = name;
    }
    
    public String getBeanName () {
        return this.beanName;
    }

}
