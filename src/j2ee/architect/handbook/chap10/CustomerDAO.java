
package j2ee.architect.handbook.chap10;

import j2ee.architect.handbook.chap09.sample1.CustomerEntity;

import java.util.List;

public interface CustomerDAO {
    
    public CustomerEntity       findById(Long customerId);    
    public void                 saveOrUpdate(CustomerEntity customer);    
    public void                 delete(Long customerId);   
    public List<CustomerEntity> findByPartialName(String name);

}
