package j2ee.architect.handbook.chap11.stubs;

import java.util.ArrayList;
import java.util.List;

/**
 * Stubbed out for example
 * @author D. Ashmore
 *
 */
public class AccountDAO {
    
    public List<AccountEntity> findByCustomerId(Long customerId) {
        return new ArrayList<AccountEntity>();
    }

}
