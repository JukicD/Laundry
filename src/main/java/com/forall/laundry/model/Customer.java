package com.forall.laundry.model;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Entity
@NamedQuery(name="Customer.findAll",query="SELECT c FROM Customer c")
public class Customer implements Serializable, Comparable<Customer> {
    
    @NotNull
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    
    @NotNull
    @Basic(fetch=FetchType.LAZY)
    private String name;

    @CollectionTable(name = "customer_product_property_map")
    @MapKeyJoinColumn(name = "prod_id")
    @ManyToMany(fetch = FetchType.EAGER)
    private Map<Product, Property> propertyMap;

    @Basic
    private Integer customerNumber;
    
    @Basic
    private String address;
    
    @Basic
    private String companyName;
    
    @Basic
    private String mailAddress;
    
    @Basic
    private String phoneNumber;
    
    @Basic
    private String zipCode;
    
    @Basic
    private BigDecimal skonto;
    
    @Basic
    private int dueTime;
    
    @Basic
    private String billingText;

    public Customer() {
        customerNumber = 0;
        propertyMap = new HashMap<>();
    }
    
    public void clearFields(){
        name = null;
        customerNumber = 0;
        address = null;
        companyName = null;
        mailAddress = null;
        phoneNumber = null;
        zipCode = null;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(int customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String eMail) {
        this.mailAddress = eMail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getBillingText() {
        return billingText;
    }

    public void setBillingText(String billingText) {
        this.billingText = billingText;
    }

    public BigDecimal getSkonto() {
        return skonto;
    }

    public void setSkonto(BigDecimal skonto) {
        this.skonto = skonto;
    }

    public int getDueTime() {
        return dueTime;
    }

    public void setDueTime(int dueTime) {
        this.dueTime = dueTime;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Customer other = (Customer) obj;
        return this.id == other.id;
    }

    @Override
    public int compareTo(Customer o) {
        return this.name.compareTo(o.name);
    }

    @Override
    public String toString() {
        return "Customer{" + "id=" + id + ", name=" + name + ", customerNumber=" + customerNumber + ", address=" + address + ", companyName=" + companyName + ", mailAddress=" + mailAddress + ", phoneNumber=" + phoneNumber + ", zipCode=" + zipCode + '}';
    }

    public Map<Product, Property> getPropertyMap() {
        return propertyMap;
    }

    public void setPropertyMap(Map<Product, Property> propertyMap) {
        this.propertyMap = propertyMap;
    }

    public void add(final Product product, final Property prop) {
        if(!propertyMap.containsKey(product)){
            propertyMap.put(product, prop);
        }
    }
}
