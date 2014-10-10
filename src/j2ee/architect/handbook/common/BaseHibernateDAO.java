package j2ee.architect.handbook.common;


import j2ee.architect.handbook.common.transaction.TransactionContext;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base Data Access Object utilizing Hibernate.
 * @author D. Ashmore
 *
 */
public abstract class BaseHibernateDAO {
    
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    protected Session getSession() {
        return TransactionContext.getCurrent().getSession();
    }

    protected Logger getLogger() {
        return logger;
    }
    

}
