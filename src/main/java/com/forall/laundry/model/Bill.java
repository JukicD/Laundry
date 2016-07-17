package com.forall.laundry.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by jd on 10/14/15.
 */

@Entity
public class Bill implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Basic
    @Temporal(TemporalType.DATE)
    private Date printed;

    @Basic
    private Long billNumber;

    @Lob
    @Basic(fetch = FetchType.EAGER)
    private byte[] bill;

    public Long getId() {
        return id;
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

    public byte[] getBill() {
        return bill;
    }

    public void setBill(byte[] bill) {
        this.bill = bill;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Bill other = (Bill) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
}
