package com.forall.laundry.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import org.hibernate.annotations.Type;

@Entity
@NamedQueries({
    @NamedQuery(name = "Ordery.findAll", query = "SELECT o FROM Ordery o"),
    @NamedQuery(name = "Ordery.findCustomersOrders", query = "SELECT o FROM Ordery o WHERE o.customer.id = :id"),
    @NamedQuery(name = "Ordery.findCustomersOldOrders", query = "SELECT o FROM Ordery o WHERE o.customer.id = :id AND o.date IS NOT NULL")})
public class Ordery implements Serializable {

    @OneToMany(fetch = FetchType.EAGER, targetEntity = Item.class)
    private List<Item> items;
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long order_id;
    
    @Basic(fetch=FetchType.LAZY)
    @Column(name = "time")
    private Date date;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="customer_id")
    private Customer customer;
    
    @Type(type = "org.hibernate.type.BinaryType")
    @Basic
    private byte[] bill;
    
    @Basic
    private boolean partOfBill;

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

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public byte[] getBill() {
        return bill;
    }

    public void setBill(byte[] bill) {
        this.bill = bill;
    }

    public boolean isPartOfBill() {
        return partOfBill;
    }

    public void setPartOfBill(boolean partOfBill) {
        this.partOfBill = partOfBill;
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
