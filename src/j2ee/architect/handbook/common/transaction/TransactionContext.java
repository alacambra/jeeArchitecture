package j2ee.architect.handbook.common.transaction;


import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.exception.ContextedRuntimeException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Transaction context that makes current session available to all Data Access objects
 * that need it.
 * @author D. Ashmore
 *
 */
public class TransactionContext {
    
    private static ThreadLocal<TransactionContext> currentTransactionContext = new ThreadLocal<TransactionContext>();
    
    private static SessionFactory   sessionFactory = null;
    private Session                 session = null;
    
    public TransactionContext() {
        Validate.notNull(sessionFactory, "Session Factory not initialized");
        
        this.session = sessionFactory.openSession();       
        this.session.beginTransaction();
        
        currentTransactionContext.set(this);
    }
    
    /**
     * Provides the current, active context for the current thread.
     * @return TransactionContext for the current thread
     */
    public static TransactionContext getCurrent() {
        TransactionContext context = currentTransactionContext.get();
        if (context == null)  {
            context = new TransactionContext();
            currentTransactionContext.set(context);
        }
        
        return context;
    }
    
    /**
     * Commits the underlying transaction.
     */
    public void commit() {
        checkContextActive();
        this.session.getTransaction().commit();
    }

    /**
     * Closes the current session and inactivates the context.
     */
    public void close() {
        checkContextActive();
        try {
            this.session.flush();
            this.session.clear();
        }
        finally {
            this.session = null;
            currentTransactionContext.remove();
        }
    }
    
    /**
     * Rolls back the current transaction.
     */
    public void rollback() {
        checkContextActive();
        this.session.getTransaction().rollback();
    }

    /**
     * Provides the current session and insures a valid transaction has begun.
     * @return
     */
    public Session getSession() {
        checkContextActive();
        
        if ( !this.session.getTransaction().isActive()) {
            this.session.beginTransaction();
        }
        return session;
    }

    private void checkContextActive() {
        if (!isActive()) {
            throw new ContextedRuntimeException("Transaction Context not currently active");
        }
    }

    public boolean isActive() {
        return this.session != null;
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void setSessionFactory(SessionFactory sessionFactory) {
        if (TransactionContext.sessionFactory != null) {
            throw new IllegalStateException("Session Factory can't be replaced once established.");
        }
        TransactionContext.sessionFactory = sessionFactory;
    }


}
