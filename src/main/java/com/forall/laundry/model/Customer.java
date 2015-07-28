package com.forall.laundry.model;


import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;

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
    
    @NotNull
    @Basic
    private int customerNumber;
    
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
    
    public Customer() {
        
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
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Customer o) {
        return this.name.compareTo(o.name);
    }

    @Override
    public String toString() {
        return "Customer{" + "id=" + id + ", name=" + name + ", customerNumber=" + customerNumber + ", address=" + address + ", companyName=" + companyName + ", mailAddress=" + mailAddress + ", phoneNumber=" + phoneNumber + ", zipCode=" + zipCode + '}';
    }
}
