
package j2ee.architect.handbook.chap10;

import j2ee.architect.handbook.common.transaction.TransactionContext;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Sample Hibernate Transaction Management Filter
 * @author D. Ashmore
 *
 */
public class TransactionManagementFilter implements Filter {
    
    private static ThreadLocal<Boolean> transactionContextState = new ThreadLocal<Boolean>();

    public void destroy() {
        // No Op

    }

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        Boolean transactionEstablished = transactionContextState.get();
        if (transactionEstablished != null) { // Transaction for this hit already started.
            chain.doFilter(request, response);
        }
        else {
            try {
                transactionContextState.set(Boolean.TRUE);
                new TransactionContext();
                
                chain.doFilter(request, response);
                TransactionContext.getCurrent().commit();
            }
            catch (Exception e) {
                TransactionContext.getCurrent().rollback();
            }
            finally {
                transactionContextState.remove();
                TransactionContext.getCurrent().close();
            }
        }

    }

    public void init(FilterConfig filterConfig) throws ServletException {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        TransactionContext.setSessionFactory(sessionFactory);
    }

}
