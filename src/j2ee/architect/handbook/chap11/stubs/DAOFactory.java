package j2ee.architect.handbook.chap11.stubs;

import j2ee.architect.handbook.chap10.CustomerDAO;
import j2ee.architect.handbook.chap10.hibernate.CustomerHibernateDAO;

/**
 * Stubbed out for example.
 * @author D. Ashmore
 *
 */
public class DAOFactory {
    
    public static CustomerDAO getCustomerDAO() {
        return new CustomerHibernateDAO();
    }
    
    public static OrderDAO getOrderDAO() {
        return new OrderDAO();
    }
    
    public static AccountDAO getAccountDAO() {
        return new AccountDAO();
    }

}
