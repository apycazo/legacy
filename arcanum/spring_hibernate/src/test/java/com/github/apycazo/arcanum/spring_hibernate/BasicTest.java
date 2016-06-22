

package com.github.apycazo.arcanum.spring_hibernate;

import com.github.apycazo.arcanum.spring_hibernate.implementations.DataBO;
import com.github.apycazo.arcanum.spring_hibernate.implementations.DataModel;
import java.util.Iterator;
import java.util.List;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Andres Picazo
 */

public class BasicTest {
    
    private DataBO dataBO;
    
    @Before
    public void init () {
        ApplicationContext appContext = new ClassPathXmlApplicationContext("spring-cfg.xml");
        // Get Business Object
        dataBO = (DataBO)appContext.getBean("dataBO");
    }
    
    @Test
    @After
    public void cleanDB () {
        dataBO.deleteAll();
        assertTrue(dataBO.readAll().isEmpty());
    }
    
    @Test
    public void testCreateData () {
        cleanDB ();
        
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
        
        // Check both are in db
        assertEquals(2, dataBO.readAll().size());
    }
    
    @Test
    public void testUpdateData () {
        testCreateData();
        List<DataModel> list = dataBO.readAll();
        Iterator<DataModel> it = list.iterator();
        DataModel dm = it.next();
        int oldAge = dm.getAge();
        int newAge = oldAge+18;
        dm.setAge(newAge);
        int code = dm.getCode();
        dataBO.update(dm);
        
        // Retrieve again
        DataModel updated = dataBO.readByCode(code);
        assertEquals(newAge, updated.getAge());
        System.out.println("Age  : " + updated.getAge());
        System.out.println("Adult: " + updated.getIsAdult());
        assertTrue(updated.getIsAdult());
    }
    
    @Test
    public void testDelete () {
        testCreateData();
        dataBO.deleteAll();
        assertTrue(dataBO.readAll().isEmpty());
    }
    
}
