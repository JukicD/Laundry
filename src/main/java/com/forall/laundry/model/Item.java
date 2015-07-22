package com.forall.laundry.model;


import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Entity
@NamedQueries({
        @NamedQuery(name = "Item.all", query = "SELECT i FROM Item i"),
        @NamedQuery(name = "Item.fromCustomer", query = "SELECT i From Item i, Ordery o, Customer c WHERE o.customer.id = :id"),
        @NamedQuery(name ="Item.fromOrder", query = "SELECT i FROM Item i WHERE i.ordery.order_id = :id")})
public class Item implements Serializable {

    @Column(unique=true,updatable=false,insertable=false,nullable=false)
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long item_id;
    
    @Column(nullable=false)
    @Basic(fetch=FetchType.LAZY)
    private Integer amount;
    
    @ManyToOne
    @JoinColumn(name="ordery_id")
    private Ordery ordery;
    
    @Basic
    private boolean borrowed;
    
    @OneToOne
    private Product item_product;
    
    @Transient
    private BigDecimal sum;
    
    @Transient
    private String name;
    
    @Transient
    private BigDecimal singlePrice;
    
    public Item() {

    }
    
    public BigDecimal getSum(){
        return item_product.getPrice().multiply(new BigDecimal(amount)).setScale(2, RoundingMode.HALF_UP);
    }
   
    public Integer getAmount() {
        return this.amount;
    }
    
    public void setName(String name){
        item_product.setName(name);
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
   
    public Long getItem_id() {
        return this.item_id;
    }
   
    public void setItem_product(Product item_product) {
        this.item_product = item_product;
    }

    public boolean isBorrowed() {
        return borrowed;
    }

    public void setBorrowed(boolean borrowed) {
        this.borrowed = borrowed;
    }

    public Ordery getOrdery() {
        return ordery;
    }

    public void setOrdery(Ordery ordery) {
        this.ordery = ordery;
    }

    public String getName() {
        return item_product.getName();
    }

    public BigDecimal getSinglePrice() {
        return item_product.getPrice();
    }

    public Product getItem_product() {
        return item_product;
    }

    public void setSinglePrice(BigDecimal singlePrice) {
        this.singlePrice = singlePrice;
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + Objects.hashCode(this.item_id);
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
        final Item other = (Item) obj;
        if (!Objects.equals(this.item_id, other.item_id)) {
            return false;
        }
        return true;
    }
}
