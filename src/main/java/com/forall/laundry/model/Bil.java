package com.forall.laundry.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by jd on 10/14/15.
 */

@Entity
public class Bil implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Ordery> orders;

    @OneToOne
    private Customer customer;

    @Basic
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date printed;

    @Basic
    private Long billNumber;
    
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] bill;

    public Long getId() {
        return id;
    }

    public List<Ordery> getOrders() {
        return orders;
    }

    public void setOrders(List<Ordery> orders) {
        this.orders = orders;
    }

    public Date getPrinted() {
        return printed;
    }

    public void setPrinted(Date printed) {
        this.printed = printed;
    }

    public Long getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(Long billNumber) {
        this.billNumber = billNumber;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public byte[] getBill() {
        return bill;
    }

    public void setBill(byte[] bill) {
        this.bill = bill;
    }
    
    
}
