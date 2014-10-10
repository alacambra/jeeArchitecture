/*
 * Created on Aug 26, 2011
 *
 * <p> Copyright 2005, Delta Vortex Technologies, Inc.   All rights reserved.
 */
package j2ee.architect.handbook.chap09.sample1;

import static org.junit.Assert.assertTrue;
import j2ee.architect.handbook.chap09.sample1.CustomerEntity;
import j2ee.architect.handbook.common.util.HibernateTestUtils;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test class for CustomerEntity.
 * @author D. Ashmore
 *
 */
public class TestCustomerEntity {
    
    private static final long TEST_CUSTOMER_ID = 666L;
    private CustomerEntity customerEntity;
    private static SessionFactory sessionFactory;
    
    @BeforeClass
    public static void setupClass() {
        
        sessionFactory = HibernateTestUtils.basicSetup(new Class[]{CustomerEntity.class});
    }
    
    @Before
    public void setup() {
        customerEntity = new CustomerEntity();
        customerEntity.setCustomerId(TEST_CUSTOMER_ID);
        customerEntity.setFirstName("Derek");
        customerEntity.setLastName("Ashmore");
        customerEntity.setMiddleInitial("C");
    }

    @Test
    public void testHashCode() {
        int test1Hash = customerEntity.hashCode();
        int test2Hash = new CustomerEntity().hashCode();
        
        assertTrue("Hashcode doesn't exist", test1Hash != 0);
        assertTrue("Hashcode doesn't exist", test2Hash != 0);
    }

    @Test
    public void testEqualsObject() throws Exception {
        boolean answer = customerEntity.equals(new CustomerEntity());
        assertTrue("Blank test", !answer);
        
        answer = customerEntity.equals(null);
        assertTrue("Null test", !answer);
        
        answer = customerEntity.equals(customerEntity);
        assertTrue("Equal test - same object", answer);
        
        CustomerEntity clone = (CustomerEntity)BeanUtils.cloneBean(customerEntity);
        clone.setCustomerId(customerEntity.getCustomerId());
        clone.setFirstName(customerEntity.getFirstName());
        clone.setLastName(customerEntity.getLastName());
        clone.setMiddleInitial(customerEntity.getMiddleInitial());
        
        answer = customerEntity.equals(clone);
        assertTrue("Equal test - clone object", answer);
    }

    @Test
    public void testToString() {
        String cStr = customerEntity.toString();
        assertTrue("String empty", !StringUtils.isEmpty(cStr));
        assertTrue("String has Derek", cStr.indexOf("Derek") >= 0);
        assertTrue("String has C", cStr.indexOf("C") >= 0);
        assertTrue("String has Ashmore", cStr.indexOf("Ashmore") >= 0);
    }
    
    @Test
    public void testGetFullName() {
        assertTrue("Basic Test", "Ashmore, Derek C.".equals(customerEntity.getFullName()));
        
        customerEntity.setMiddleInitial(null);
        assertTrue("Null Middle Initial", "Ashmore, Derek".equals(customerEntity.getFullName()));
    }
    
    @Test
    public void testAnnotations() {
        Session session = sessionFactory.openSession();
        
        session.saveOrUpdate(customerEntity);
        
        CustomerEntity readEntity = (CustomerEntity)session.get(CustomerEntity.class, Long.valueOf(TEST_CUSTOMER_ID));
        assertTrue("Entity found", readEntity != null);
        assertTrue("Entity was the right one", readEntity.getCustomerId() == TEST_CUSTOMER_ID);
    }

}
