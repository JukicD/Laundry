package com.forall.laundry.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Ordery implements Serializable {

    @OneToMany(fetch = FetchType.EAGER,targetEntity = Item.class)
    private List<Item> items;
    
    @Column(unique=true,updatable=false,insertable=false,nullable=false)
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long order_id;
    
    @Basic(fetch=FetchType.LAZY)
    private Date date;
    
    @ManyToOne
    @JoinColumn(name="customer_id")
    private Customer customer;

    public Ordery() {
       items = new ArrayList<>();
    }
   
    public void addItem(Item item){
        items.add(item);
    }
    public List<Item> getOrdery_item() {
        return this.items;
    }

    public void setOrdery_item(List<Item> ordery_item) {
        this.items = ordery_item;
    }
   
    public Long getOrder_id() {
        return this.order_id;
    }

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.order_id);
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
        final Ordery other = (Ordery) obj;
        if (!Objects.equals(this.order_id, other.order_id)) {
            return false;
        }
        return true;
    }
}
