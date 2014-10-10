/*
 * Created on Aug 25, 2011
 *
 */
package j2ee.architect.handbook.chap09.sample1;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


/**
 * Sample entity class representing a customer
 * @author D. Ashmore
 *
 */
@Entity (name="Customer")
public class CustomerEntity implements Serializable {

    private static final long serialVersionUID = 33307895945275341L;
    
    private Long    customerId;
    private String  lastName;
    private String  firstName;
    private String  middleInitial;
    
    @Column(name="Customer_Id")
    @Id
    public Long getCustomerId() {
        return customerId;
    }
    
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
    
    @Column(name="Last_Name")
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    @Column(name="First_Name")
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    @Column(name="Middle_Initial")
    public String getMiddleInitial() {
        return middleInitial;
    }
    
    public void setMiddleInitial(String middleInitial) {
        this.middleInitial = middleInitial;
    }
    
    /**
     * Convenience method to combine first, last names and middle initial if present.
     * @return Name expressed as lastName, firstName m.
     */
    @Transient
    public String getFullName() {
        StringBuffer nameBuffer = new StringBuffer();
        nameBuffer.append(this.getLastName());
        nameBuffer.append(",");
        
        if ( !StringUtils.isEmpty(this.getFirstName())) {
            nameBuffer.append(" ");
            nameBuffer.append(this.getFirstName());
        }
        if ( !StringUtils.isEmpty(this.getMiddleInitial())) {
            nameBuffer.append(" ");
            nameBuffer.append(this.getMiddleInitial());
            nameBuffer.append(".");
        }
        return nameBuffer.toString();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).
                append(this.customerId).
                append(this.lastName).
                append(this.firstName).
                append(this.middleInitial).
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
        CustomerEntity rhs = (CustomerEntity) obj;
        return new EqualsBuilder()
                      .append(this.customerId, rhs.customerId)
                      .append(this.lastName, rhs.lastName)
                      .append(this.firstName, rhs.firstName)
                      .append(this.middleInitial, rhs.middleInitial)
                      .isEquals();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("customerId", this.customerId)
                .append("lastName", this.lastName)
                .append("firstName", this.firstName)
                .append("middleInitial", this.middleInitial)
                .toString();
    }
    
}
