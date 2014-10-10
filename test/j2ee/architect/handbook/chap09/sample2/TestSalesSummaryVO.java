/*
 * Created on Aug 28, 2011
 *
 * <p> Copyright 2005, Delta Vortex Technologies, Inc.   All rights reserved.
 */
package j2ee.architect.handbook.chap09.sample2;

import static org.junit.Assert.assertTrue;
import j2ee.architect.handbook.chap09.sample2.SalesSummaryVO;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for SalesSummaryVO
 * @author D. Ashmore
 *
 */
public class TestSalesSummaryVO {
    
    private SalesSummaryVO salesSummary;

    @Before
    public void setUp() throws Exception {
        salesSummary = new SalesSummaryVO();
        
        salesSummary.setSalesRegion("Midwest");
        salesSummary.setSalesYear(2011);
        salesSummary.setSalesQuarter1(100000.00D);
        salesSummary.setSalesQuarter2(200000.00D);
        salesSummary.setSalesQuarter3(300000.00D);
        salesSummary.setSalesQuarter4(400000.00D);
    }

    @Test
    public void testHashCode() {
        int test1Hash = salesSummary.hashCode();
        int test2Hash = new SalesSummaryVO().hashCode();
        
        assertTrue("Hashcode doesn't exist", test1Hash != 0);
        assertTrue("Hashcode doesn't exist", test2Hash != 0);
    }

    @Test
    public void testEqualsObject() throws Exception {
        boolean answer = salesSummary.equals(new SalesSummaryVO());
        assertTrue("Blank test", !answer);
        
        answer = salesSummary.equals(null);
        assertTrue("Null test", !answer);
        
        answer = salesSummary.equals(salesSummary);
        assertTrue("Equal test - same object", answer);
        
        SalesSummaryVO clone = (SalesSummaryVO)BeanUtils.cloneBean(salesSummary);       
        answer = salesSummary.equals(clone);
        assertTrue("Equal test - clone object", answer);
    }

    @Test
    public void testToString() {
        String cStr = salesSummary.toString();
        assertTrue("String empty", !StringUtils.isEmpty(cStr));
        assertTrue("String has Midwest", cStr.indexOf("Midwest") >= 0);
        assertTrue("String has 2011", cStr.indexOf("2011") >= 0);
        assertTrue("String has 100000", cStr.indexOf("100000") >= 0);
        assertTrue("String has 200000", cStr.indexOf("200000") >= 0);
        assertTrue("String has 300000", cStr.indexOf("300000") >= 0);
        assertTrue("String has 400000", cStr.indexOf("400000") >= 0);
    }

    @Test
    public void testCompareTo() throws Exception {
        int answer = salesSummary.compareTo(new SalesSummaryVO()) ;
        assertTrue("Compare test - blank", answer > 0);
        
        answer = salesSummary.compareTo(salesSummary) ;
        assertTrue("Compare test - same", answer == 0);
        
        SalesSummaryVO clone = (SalesSummaryVO)BeanUtils.cloneBean(salesSummary);       
        answer = salesSummary.compareTo(clone);
        assertTrue("Compare test - clone object", answer == 0);
    }
    
    @Test
    public void testClone() throws Exception {
        SalesSummaryVO clone = (SalesSummaryVO)this.salesSummary.clone();
        assertTrue("Clone test - equals", clone.equals(this.salesSummary));
    }

}
