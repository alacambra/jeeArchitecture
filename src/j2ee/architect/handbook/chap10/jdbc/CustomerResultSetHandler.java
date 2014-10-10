
package j2ee.architect.handbook.chap10.jdbc;

import j2ee.architect.handbook.chap09.sample1.CustomerEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;

/**
 * Sample Result Set Handler implementation for Customer Entities.
 * @author D. Ashmore
 *
 */
public class CustomerResultSetHandler implements ResultSetHandler<List<CustomerEntity>> {

    /* (non-Javadoc)
     * @see org.apache.commons.dbutils.ResultSetHandler#handle(java.sql.ResultSet)
     */
    public List<CustomerEntity> handle(ResultSet rs) throws SQLException {
        
        List<CustomerEntity> list = new ArrayList<CustomerEntity>();
        CustomerEntity customerEntity = null;
        
        while (rs.next()) {
            customerEntity = new CustomerEntity();
            list.add(customerEntity);
            
            customerEntity.setCustomerId(rs.getLong("Customer_Id"));
            customerEntity.setFirstName(rs.getString("First_Name"));
            customerEntity.setLastName(rs.getString("Last_Name"));
            customerEntity.setMiddleInitial(rs.getString("Middle_Initial"));
        }
        return list;
    }

}
