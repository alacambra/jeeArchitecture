/*
 * Created on Aug 27, 2011
 *
 * <p> Copyright 2005, Delta Vortex Technologies, Inc.   All rights reserved.
 */
package j2ee.architect.handbook.chap09.sample2;

import org.apache.commons.lang3.builder.CompareToBuilder;

import j2ee.architect.handbook.common.BaseVO;

public class SalesSummaryVO extends BaseVO implements Comparable<SalesSummaryVO>, Cloneable {
    
    private static final long serialVersionUID = 8438048482255193208L;
    
    private String  salesRegion;
    private Integer salesYear;
    private Double  salesQuarter1;
    private Double  salesQuarter2;
    private Double  salesQuarter3;
    private Double  salesQuarter4;
    
    public String getSalesRegion() {
        return salesRegion;
    }
    
    public void setSalesRegion(String salesRegion) {
        this.salesRegion = salesRegion;
    }
    
    public Integer getSalesYear() {
        return salesYear;
    }
    
    public void setSalesYear(Integer salesYear) {
        this.salesYear = salesYear;
    }
    
    public Double getSalesQuarter1() {
        return salesQuarter1;
    }
    
    public void setSalesQuarter1(Double salesQuarter1) {
        this.salesQuarter1 = salesQuarter1;
    }
    
    public Double getSalesQuarter2() {
        return salesQuarter2;
    }
    
    public void setSalesQuarter2(Double salesQuarter2) {
        this.salesQuarter2 = salesQuarter2;
    }
    
    public Double getSalesQuarter3() {
        return salesQuarter3;
    }
    
    public void setSalesQuarter3(Double salesQuarter3) {
        this.salesQuarter3 = salesQuarter3;
    }
    
    public Double getSalesQuarter4() {
        return salesQuarter4;
    }
    
    public void setSalesQuarter4(Double salesQuarter4) {
        this.salesQuarter4 = salesQuarter4;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(SalesSummaryVO o) {
        return new CompareToBuilder()
        .append(this.salesYear, o.salesYear)
        .append(this.salesRegion, o.salesRegion)
        .append(this.salesQuarter1, o.salesQuarter1)
        .append(this.salesQuarter2, o.salesQuarter2)
        .append(this.salesQuarter3, o.salesQuarter3)
        .append(this.salesQuarter4, o.salesQuarter4)
        .toComparison();
    }

}
