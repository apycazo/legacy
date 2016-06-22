
package nox.apycazo.hibernateannotatedexample;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class HibernateUtil {

    private static final SessionFactory sessionFactory;

    static {
        try {
            // Si no ponemos fichero, intenta cargar "/hibernate.cfg.xml" en el raiz
            sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
//            sessionFactory = new AnnotationConfiguration().configure(
//                    new java.io.File("classpath:/resources/hibernate.cfg.xml")).buildSessionFactory();
        } catch (Throwable ex) {
            // Log exception!
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() throws HibernateException {
        return sessionFactory.openSession();
    }
}