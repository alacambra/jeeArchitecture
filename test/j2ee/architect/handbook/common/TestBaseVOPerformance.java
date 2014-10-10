/*
 * Created on Aug 28, 2011
 *
 * <p> Copyright 2005, Delta Vortex Technologies, Inc.   All rights reserved.
 */
package j2ee.architect.handbook.common;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class to measure performance between custom toString(), equals(), and hashCode()
 * implementations and generic implementations inherited from BaseVO.
 * @author D. Ashmore
 *
 */
public class TestBaseVOPerformance {
    
    private j2ee.architect.handbook.chap09.sample1.SalesSummaryVO salesSummaryCustom;
    private j2ee.architect.handbook.chap09.sample2.SalesSummaryVO salesSummaryGeneric;
    private StopWatch stopWatch;

    @Before
    public void setUp() throws Exception {
        salesSummaryCustom = new j2ee.architect.handbook.chap09.sample1.SalesSummaryVO();
        salesSummaryCustom.setSalesRegion("Midwest");
        salesSummaryCustom.setSalesYear(2011);
        salesSummaryCustom.setSalesQuarter1(100000.00D);
        salesSummaryCustom.setSalesQuarter2(200000.00D);
        salesSummaryCustom.setSalesQuarter3(300000.00D);
        salesSummaryCustom.setSalesQuarter4(400000.00D);
        
        salesSummaryGeneric = new j2ee.architect.handbook.chap09.sample2.SalesSummaryVO();
        salesSummaryGeneric.setSalesRegion("Midwest");
        salesSummaryGeneric.setSalesYear(2011);
        salesSummaryGeneric.setSalesQuarter1(100000.00D);
        salesSummaryGeneric.setSalesQuarter2(200000.00D);
        salesSummaryGeneric.setSalesQuarter3(300000.00D);
        salesSummaryGeneric.setSalesQuarter4(400000.00D);
        
        stopWatch = new StopWatch();
    }

    @SuppressWarnings("unused")
    @Test
    public void testHashCodePerformance() {
        int nbrIterations = 100000;
        
        stopWatch.start();
        int tempInt;
        
        for (int i = 0; i < nbrIterations; i++) {
            tempInt = salesSummaryCustom.hashCode();
        }
        
        stopWatch.stop();
        System.out.println("Custom Hashcode with " + nbrIterations + " iterations: " + stopWatch.getTime() + " ms.");
        
        stopWatch.reset();
        stopWatch.start();
        for (int i = 0; i < nbrIterations; i++) {
            tempInt = salesSummaryGeneric.hashCode();
        }
        
        stopWatch.stop();
        System.out.println("Generic Hashcode with " + nbrIterations + " iterations: " + stopWatch.getTime() + " ms.");
    }
    
    @SuppressWarnings("unused")
    @Test
    public void testEqualsPerformance() throws Exception {
        j2ee.architect.handbook.chap09.sample1.SalesSummaryVO salesSummaryCustomClone = 
                (j2ee.architect.handbook.chap09.sample1.SalesSummaryVO)BeanUtils.cloneBean(salesSummaryCustom);
        j2ee.architect.handbook.chap09.sample2.SalesSummaryVO salesSummaryGenericClone = 
                (j2ee.architect.handbook.chap09.sample2.SalesSummaryVO)BeanUtils.cloneBean(salesSummaryGeneric);
        
        int nbrIterations = 100000;
        
        stopWatch.start();
        boolean tempBool;
        
        for (int i = 0; i < nbrIterations; i++) {
            tempBool = salesSummaryCustom.equals(salesSummaryCustomClone);
        }
        
        stopWatch.stop();
        System.out.println("Custom equals with " + nbrIterations + " iterations: " + stopWatch.getTime() + " ms.");
        
        stopWatch.reset();
        stopWatch.start();
        for (int i = 0; i < nbrIterations; i++) {
            tempBool = salesSummaryGeneric.equals(salesSummaryGenericClone);
        }
        
        stopWatch.stop();
        System.out.println("Generic equals with " + nbrIterations + " iterations: " + stopWatch.getTime() + " ms.");
    }
    
    @SuppressWarnings("unused")
    @Test
    public void testToStringPerformance() {
        int nbrIterations = 100000;
        
        stopWatch.start();
        String tempStr;
        
        for (int i = 0; i < nbrIterations; i++) {
            tempStr = salesSummaryCustom.toString();
        }
        
        stopWatch.stop();
        System.out.println("Custom ToString() with " + nbrIterations + " iterations: " + stopWatch.getTime() + " ms.");
        
        stopWatch.reset();
        stopWatch.start();
        for (int i = 0; i < nbrIterations; i++) {
            tempStr = salesSummaryGeneric.toString();
        }
        
        stopWatch.stop();
        System.out.println("Generic ToString() with " + nbrIterations + " iterations: " + stopWatch.getTime() + " ms.");
    }
    
    @SuppressWarnings("unused")
    @Test
    public void testClone() throws Exception {
        int nbrIterations = 100000;
        
        stopWatch.start();
        Object tempObj;
        
        for (int i = 0; i < nbrIterations; i++) {
            tempObj = salesSummaryCustom.clone();
        }
        
        stopWatch.stop();
        System.out.println("Custom Clone() with " + nbrIterations + " iterations: " + stopWatch.getTime() + " ms.");
        
        stopWatch.reset();
        stopWatch.start();
        for (int i = 0; i < nbrIterations; i++) {
            tempObj = salesSummaryGeneric.clone();
        }
        
        stopWatch.stop();
        System.out.println("Generic Clone() with " + nbrIterations + " iterations: " + stopWatch.getTime() + " ms.");
    }
    

}
