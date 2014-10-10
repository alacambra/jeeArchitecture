
package j2ee.architect.handbook.chap10.hibernate;

import j2ee.architect.handbook.chap10.CustomerDAO;
import j2ee.architect.handbook.chap10.TestCustomerDAO;
import j2ee.architect.handbook.chap10.hibernate.CustomerHibernateDAO;

public class TestCustomerHibernateDAO extends TestCustomerDAO {
    
    /* (non-Javadoc)
     * @see j2ee.architect.handbook.chap11.TestCustomerDAO#createCustomerDAO()
     */
    @Override
    protected CustomerDAO createCustomerDAO() {
        return new CustomerHibernateDAO();
    }

}
