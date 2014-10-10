package j2ee.architect.handbook.chap11;

import java.util.List;

import j2ee.architect.handbook.chap09.sample1.CustomerEntity;
import j2ee.architect.handbook.chap11.stubs.AccountEntity;
import j2ee.architect.handbook.chap11.stubs.DAOFactory;
import j2ee.architect.handbook.common.BusinessProcessingException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

/**
 * Example Business Logic Layer class
 * @author D. Ashmore
 *
 */
public class Customer {
    
    public void saveOrUpdate(CustomerEntity customer) 
            throws BusinessProcessingException {
        Validate.notNull(customer, "Null customer not allowed");
        
        BusinessProcessingException busException = 
                new BusinessProcessingException("Customer cannot be saved");
        
        // Business Level Validation performed here.
        if (StringUtils.isEmpty(customer.getLastName())) {
            busException.addContextValue("lastName", "Last Name is required.");
        }
        if (StringUtils.isEmpty(customer.getFirstName())) {
            busException.addContextValue("firstName", 
                    "First Name is required.");
        }        
        checkForBusinessException(busException);
        
        DAOFactory.getCustomerDAO().saveOrUpdate(customer);
        
    }
    
    public void delete(CustomerEntity customer) 
            throws BusinessProcessingException {
        Validate.notNull(customer, "Null customer not allowed");
        Validate.notNull(customer.getCustomerId(), 
                "Null customer Id not allowed");
        
        BusinessProcessingException busException = 
                new BusinessProcessingException("Customer cannot be deleted");
        
        // Business level validation performed here.
        if (DAOFactory.getOrderDAO().findByCustomerId(
                customer.getCustomerId()).size() > 0) {
            busException.addContextValue("deleteError", 
                    "Customers with purchase orders can't be deleted;" + 
                    " they must be inactivated instead.");
        }
        
        List<AccountEntity> accountList = 
                DAOFactory.getAccountDAO().findByCustomerId(
                        customer.getCustomerId());
        for (AccountEntity account: accountList) {
            if (account.isActive()) {
                busException.addContextValue("deleteError", 
                        "Customers with active accounts can't be deleted;" + 
                        " they must be inactivated instead.");
                break;
            }
        }
        
        // throw Business Exception if any discovered.
        checkForBusinessException(busException);        
        DAOFactory.getCustomerDAO().delete(customer.getCustomerId());
        
    }
    
    private void checkForBusinessException(
            BusinessProcessingException busException)
            throws BusinessProcessingException {
        if (busException.getContextEntries().size() > 0) {
            throw busException;
        }
    }

}
