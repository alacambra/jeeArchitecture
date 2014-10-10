
package j2ee.architect.handbook.chap10;

import static org.junit.Assert.assertTrue;
import j2ee.architect.handbook.chap09.sample1.CustomerEntity;
import j2ee.architect.handbook.chap10.CustomerDAO;
import j2ee.architect.handbook.chap10.hibernate.CustomerHibernateDAO;
import j2ee.architect.handbook.common.transaction.TransactionContext;
import j2ee.architect.handbook.common.util.HibernateTestUtils;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public abstract class TestCustomerDAO {
    
    private static final long TEST_CUSTOMER_ID = 666L;
    private CustomerDAO dao = new CustomerHibernateDAO();
    
    protected abstract CustomerDAO createCustomerDAO();

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    	if (TransactionContext.getSessionFactory() == null) {
	        SessionFactory sessionFactory = HibernateTestUtils.basicSetup(new Class[]{CustomerEntity.class});
	        TransactionContext.setSessionFactory(sessionFactory);
    	}
    }

    @Before
    public void setUp() throws Exception {
        new TransactionContext();
    }

    @SuppressWarnings("deprecation")
    @After
    public void tearDown() throws Exception {
        QueryRunner queryRunner = new QueryRunner();
        queryRunner.update(TransactionContext.getCurrent().getSession().connection(), "delete from Customer");
        TransactionContext.getCurrent().commit();
        TransactionContext.getCurrent().close();
    }

    @Test
    public void testFindById() {
        CustomerEntity customerEntity = createTestEntity();
        
        dao.saveOrUpdate(customerEntity);
        TransactionContext.getCurrent().commit();
        
        CustomerEntity readEntity = dao.findById(TEST_CUSTOMER_ID);
        assertTrue("Find by Id and SaveOrUpdate test", readEntity.equals(customerEntity));
        
        dao.delete(TEST_CUSTOMER_ID);
        TransactionContext.getCurrent().commit();
        
        readEntity = dao.findById(TEST_CUSTOMER_ID);
        assertTrue("Delete test", readEntity== null);
    }

    private CustomerEntity createTestEntity() {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setCustomerId(TEST_CUSTOMER_ID);
        customerEntity.setFirstName("Derek");
        customerEntity.setLastName("Ashmore");
        customerEntity.setMiddleInitial("C");
        
        return customerEntity;
    }

    @Test
    public void testFindByPartialName() {
        CustomerEntity customerEntity = createTestEntity();
        
        dao.saveOrUpdate(customerEntity);
        TransactionContext.getCurrent().commit();
        
        List<CustomerEntity> list = dao.findByPartialName("sh");
        assertTrue("findByPartialName test 1", list.size() == 1);
        
        list = dao.findByPartialName("z");
        assertTrue("findByPartialName test 2", list.size() == 0);
    }

}
