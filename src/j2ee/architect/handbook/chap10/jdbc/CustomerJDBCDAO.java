
package j2ee.architect.handbook.chap10.jdbc;

import java.util.List;

import org.apache.commons.lang3.Validate;

import j2ee.architect.handbook.chap09.sample1.CustomerEntity;
import j2ee.architect.handbook.chap10.CustomerDAO;
import j2ee.architect.handbook.common.BaseJDBCDAO;

/**
 * Sample JDBC-based DAO leveraging Apache Commons DBUtils.
 * @author D. Ashmore
 *
 */
public class CustomerJDBCDAO extends BaseJDBCDAO implements CustomerDAO {

    public CustomerEntity findById(Long customerId) {
        Validate.notNull(customerId, "Null customerId not allowed.");
        List<CustomerEntity> list = this.query(
                "select * from Customer where Customer_Id = ?", 
                new Object[]{customerId}, new CustomerResultSetHandler());
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public void saveOrUpdate(CustomerEntity customer) {
        Validate.notNull(customer, "Null customer not allowed.");
        CustomerEntity readEntity = this.findById(customer.getCustomerId());
        String sqlText;
        Object[] params;
        
        if (readEntity == null) { // Insert
            sqlText = "insert into Customer " +
                    "(Customer_Id, Last_Name, First_Name, Middle_Initial ) " +
                    "values (?,?,?,?)";
            
            params = new Object[]{customer.getCustomerId()
                    , customer.getLastName()
                    , customer.getFirstName()
                    , customer.getMiddleInitial()};
        }
        else { // update
            sqlText = "update Customer " +
                    "set Last_Name = ? " +
                    ", First_Name = ? " +
                    ", Middle_Initial = ? " +
                    "where Customer_Id = ?";
            
            params = new Object[]{customer.getLastName()
                    , customer.getFirstName()
                    , customer.getMiddleInitial()
                    , customer.getCustomerId()};
        }
        
        this.update(sqlText, params);

    }

    public void delete(Long customerId) {
        Validate.notNull(customerId, "Null customerId not allowed.");
        this.update("delete from Customer where Customer_Id = ?", 
                new Object[]{customerId});

    }

    public List<CustomerEntity> findByPartialName(String name) {
        Validate.notEmpty(name, "Null or blank name not allowed.");
        return this.query(
                "select * from Customer where last_name like ? or first_name like ?", 
                new Object[]{name,name}, new CustomerResultSetHandler());
    }

}
