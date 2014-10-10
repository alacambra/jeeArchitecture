package j2ee.architect.handbook.chap10.hibernate;

import j2ee.architect.handbook.chap09.sample1.CustomerEntity;
import j2ee.architect.handbook.chap10.CustomerDAO;
import j2ee.architect.handbook.common.BaseHibernateDAO;

import java.util.List;

import org.apache.commons.lang3.Validate;
import org.hibernate.SQLQuery;

/**
 * Example Hibernate Data Access Object (DAO)
 * @author D. Ashmore
 *
 */
public class CustomerHibernateDAO extends BaseHibernateDAO implements
        CustomerDAO {

    public CustomerEntity findById(Long customerId) {
        Validate.notNull(customerId, "Null customerId not allowed.");
        return (CustomerEntity)this.getSession()
                .get(CustomerEntity.class, customerId);
    }

    public void saveOrUpdate(CustomerEntity customer) {
        Validate.notNull(customer, "Null customer not allowed.");
        this.getSession().saveOrUpdate(customer);
    }

    public void delete(Long customerId) {
        Validate.notNull(customerId, "Null customerId not allowed.");
        CustomerEntity customer = this.findById(customerId);
        
        if (customer != null) {
            this.getSession().delete(customer);
        }
    }

    @SuppressWarnings("unchecked")
    public List<CustomerEntity> findByPartialName(String name) {
        Validate.notEmpty(name, "Null or blank name not allowed.");
        SQLQuery sql = this.getSession().createSQLQuery(
                "select * from Customer " + 
                "where last_name like :name or first_name like :name");
        sql.setParameter("name", "%" + name + "%");
        sql.addEntity(CustomerEntity.class);
        
        return sql.list();
    }

}
