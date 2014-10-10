/*
 * Created on Sep 25, 2011
 *
 * <p> Copyright 2005, Delta Vortex Technologies, Inc.   All rights reserved.
 */
package j2ee.architect.handbook.common.transaction;

import static org.junit.Assert.assertTrue;
import j2ee.architect.handbook.chap09.sample1.CustomerEntity;
import j2ee.architect.handbook.common.util.HibernateTestUtils;

import org.apache.commons.dbutils.QueryRunner;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestTransactionContext {
    
    private static SessionFactory sessionFactory;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    	if (TransactionContext.getSessionFactory() == null) {
	        sessionFactory = HibernateTestUtils.basicSetup(new Class[]{CustomerEntity.class});
	        TransactionContext.setSessionFactory(sessionFactory);
    	}
        new TransactionContext();
    }

    @Before
    public void setUp() throws Exception {
        new TransactionContext();
    }
    
    @After
    public void tearDown() throws Exception {
        QueryRunner queryRunner = new QueryRunner();
        queryRunner.update(TransactionContext.getCurrent().getSession().connection(), "delete from Customer");
        TransactionContext.getCurrent().commit();
        TransactionContext.getCurrent().close();
    }

    @Test
    public void testCommit() throws Exception {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setCustomerId(666L);
        customerEntity.setFirstName("Derek");
        customerEntity.setLastName("Ashmore");
        customerEntity.setMiddleInitial("C");
        
        TransactionContext.getCurrent().getSession().saveOrUpdate(customerEntity);
        TransactionContext.getCurrent().commit();
        
        customerEntity = (CustomerEntity)TransactionContext.getCurrent().getSession().get(CustomerEntity.class, new Long(666));
        
        assertTrue("Customer entity not found", customerEntity != null);
        assertTrue("Customer entity not found by id", customerEntity.getLastName().equals("Ashmore"));
        
        TransactionContext.getCurrent().getSession().delete(customerEntity);
        TransactionContext.getCurrent().commit();
        customerEntity = (CustomerEntity)TransactionContext.getCurrent().getSession().get(CustomerEntity.class, new Long(666));
        assertTrue("Customer entity found, but should not be", customerEntity == null);
    }
    
    @Test
    public void testRollback() throws Exception {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setCustomerId(666L);
        customerEntity.setFirstName("Derek");
        customerEntity.setLastName("Ashmore");
        customerEntity.setMiddleInitial("C");
               
        TransactionContext.getCurrent().getSession().saveOrUpdate(customerEntity);
        TransactionContext.getCurrent().rollback();
  
        customerEntity = (CustomerEntity)TransactionContext.getCurrent().getSession().load(CustomerEntity.class, new Long(666));
        assertTrue("Customer entity found, but should not be", customerEntity != null);
    }
    
    @Test
    public void testMultipleTransactions() throws Exception {
        this.testCommit();
        
        TransactionContext.getCurrent().close();
        new TransactionContext();
        
        this.testCommit();
        
        TransactionContext.getCurrent().close();
        new TransactionContext();
        
        this.testCommit();
        
        TransactionContext.getCurrent().close();
        new TransactionContext();
    }

}
