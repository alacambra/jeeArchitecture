/*
 * Created on Aug 27, 2011
 *
 * <p> Copyright 2005, Delta Vortex Technologies, Inc.   All rights reserved.
 */
package j2ee.architect.handbook.chap09.sample1;

import java.io.Serializable;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Sample Value Object representing aggregate sales information.
 * @author D. Ashmore
 *
 */
public class SalesSummaryVO implements Serializable, Comparable<SalesSummaryVO> {
    
    private static final long serialVersionUID = -4099990023592442788L;
    
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
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).
                append(this.salesRegion).
                append(this.salesYear).
                append(this.salesQuarter1).
                append(this.salesQuarter2).
                append(this.salesQuarter3).
                append(this.salesQuarter4).
                toHashCode();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj == this) { return true; }
        if (obj.getClass() != getClass()) {
          return false;
        }
        SalesSummaryVO rhs = (SalesSummaryVO) obj;
        return new EqualsBuilder()
                      .append(this.salesRegion, rhs.salesRegion)
                      .append(this.salesYear, rhs.salesYear)
                      .append(this.salesQuarter1, rhs.salesQuarter1)
                      .append(this.salesQuarter2, rhs.salesQuarter2)
                      .append(this.salesQuarter3, rhs.salesQuarter3)
                      .append(this.salesQuarter4, rhs.salesQuarter4)
                      .isEquals();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("salesRegion", this.salesRegion)
                .append("salesYear", this.salesYear)
                .append("salesQuarter1", this.salesQuarter1)
                .append("salesQuarter2", this.salesQuarter2)
                .append("salesQuarter3", this.salesQuarter3)
                .append("salesQuarter4", this.salesQuarter4)
                .toString();
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

    /* (non-Javadoc)
     * @see java.lang.Object#clone()
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        SalesSummaryVO clone = new SalesSummaryVO();
        clone.salesYear = this.salesYear;
        clone.salesRegion = this.salesRegion;
        clone.salesQuarter1 = this.salesQuarter1;
        clone.salesQuarter2 = this.salesQuarter2;
        clone.salesQuarter3 = this.salesQuarter3;
        clone.salesQuarter4 = this.salesQuarter4;
        return clone;
    }
    

}
