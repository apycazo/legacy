package com.github.apycazo.arcanum.spring_hibernate;

import com.github.apycazo.arcanum.spring_hibernate.implementations.DataBO;
import com.github.apycazo.arcanum.spring_hibernate.implementations.DataModel;
import java.util.Iterator;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class App {
    
    // This is actually useless, to test use junit (see test packages).
    
    public static void main( String[] args ) {
        // Load spring
        ApplicationContext appContext = new ClassPathXmlApplicationContext("spring-cfg.xml");
        // Get Business Object
        DataBO dataBO = (DataBO)appContext.getBean("dataBO");
        // Check if null
        if (dataBO == null) {
            System.err.println("No Business Object found!!");
            System.exit(-1);
        }
        // Else, create a few entries
        DataModel data_1 = new DataModel();
        data_1.setName("John");
        data_1.setAge(25);
        data_1.setCode(100);
        
        DataModel data_2 = new DataModel();
        data_2.setName("Smith");
        data_2.setAge(30);
        data_2.setCode(101);
        
        // Save entries using BO
        dataBO.create(data_1);
        dataBO.create(data_2);
        
        // Retrieve by code
        DataModel dm = dataBO.readByCode(101);
        
        System.out.println("Read element: " + dm.getName());
        
        // Read all elements
        List<DataModel> list = dataBO.readAll();
        System.out.println("Found : " + list.size() + " elements:");
        Iterator<DataModel> it = list.iterator();
        while (it.hasNext()) {
            System.out.println("\t"+it.next().toString());
        }
        
        // Delete id=101
        System.out.println("Deleting...");
        dataBO.delete(dm);
        System.out.println("Remaining: " + dataBO.readAll().size() + " elements");
        
        System.out.println("Done");
        
    }
}
