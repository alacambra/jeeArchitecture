package j2ee.architect.handbook.chap11.stubs;

import java.util.ArrayList;
import java.util.List;

/**
 * Stubbed out for example
 * @author D. Ashmore
 *
 */
public class OrderDAO {
    
    public List<PurchaseOrderEntity> findByCustomerId(Long customerId) {
        return new ArrayList<PurchaseOrderEntity>();
    }

}
