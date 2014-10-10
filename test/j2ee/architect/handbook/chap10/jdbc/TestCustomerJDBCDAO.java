/*
 * Created on Sep 25, 2011
 *
 * <p> Copyright 2005, Delta Vortex Technologies, Inc.   All rights reserved.
 */
package j2ee.architect.handbook.chap10.jdbc;

import j2ee.architect.handbook.chap10.CustomerDAO;
import j2ee.architect.handbook.chap10.TestCustomerDAO;
import j2ee.architect.handbook.chap10.jdbc.CustomerJDBCDAO;

public class TestCustomerJDBCDAO extends TestCustomerDAO {

    /* (non-Javadoc)
     * @see j2ee.architect.handbook.chap11.TestCustomerDAO#createCustomerDAO()
     */
    @Override
    protected CustomerDAO createCustomerDAO() {
        return new CustomerJDBCDAO();
    }

}
