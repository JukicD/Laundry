package com.forall.laundry.model;


import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
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

    @Basic
    private String address;
    
    @NotNull
    @Column(nullable=false)
    @Basic(fetch=FetchType.LAZY)
    private String name;
    
    @Column(nullable=false)
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    public Customer() {
        
    }
   
    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setId(int id) {
        this.id = id;
    }
   
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
   
    public int getId() {
        return this.id;
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
}
