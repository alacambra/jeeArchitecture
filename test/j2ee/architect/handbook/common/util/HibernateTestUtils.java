package j2ee.architect.handbook.common.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Generic Hibernate Utilities fo Test Cases.
 * @author D. Ashmore
 *
 */
public class HibernateTestUtils {
    
    /**
     * Performs a basic Hibernate configuration suitable for test cases.
     * @param entityClassList
     * @return sessionFactory
     */
    @SuppressWarnings("rawtypes")
    public static SessionFactory basicSetup(Class[] entityClassList) {
        SessionFactory factory = null;
        
        Configuration cfg = new Configuration();
        
        if (entityClassList != null) {
            for (Class annotatedClass: entityClassList) {
                cfg.addAnnotatedClass(annotatedClass);
            }
        }
        
        cfg.setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect")
            .setProperty("hibernate.connection.driver_class", "org.hsqldb.jdbcDriver")
            .setProperty("hibernate.connection.url", "jdbc:hsqldb:mem:aname")
            .setProperty("hibernate.connection.username", "sa")
            .setProperty("hibernate.connection.password", "")
            .setProperty("hibernate.connection.pool_size", "3")
            .setProperty("hibernate.connection.autocommit", "false")
            .setProperty("hibernate.hbm2ddl.auto", "create");
        
        factory = cfg.buildSessionFactory();        
        return factory;
        
    }

}
