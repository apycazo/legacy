
package com.github.apycazo.arcanum.spring_hibernate.implementations;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * Workaround to autowire session factory to actual dao class
 * @author Andres Picazo
 */
public abstract class DataHibernateSupport extends HibernateDaoSupport {
    
    @Autowired
    public void anyMethodName(SessionFactory sessionFactory)
    {
        setSessionFactory(sessionFactory);
    }
    
}
