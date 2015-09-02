package com.forall.laundry.model;


import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@Entity
@NamedQueries({
        @NamedQuery(name = "Item.all", query = "SELECT i FROM Item i"),
        @NamedQuery(name = "Item.fromCustomer", query = "SELECT i From Item i, Ordery o, Customer c WHERE o.customer.id = :id"),
        @NamedQuery(name ="Item.fromOrder", query = "SELECT i FROM Item i WHERE i.ordery.id = :id")})
public class Item implements Serializable {

    @Column(unique=true,updatable=false,insertable=false,nullable=false)
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer item_id;
    
    @Column(nullable=false)
    @Basic(fetch=FetchType.LAZY)
    private Integer amount;
    
    @ManyToOne
    @JoinColumn(name="ordery_id")
    private Ordery ordery;
    
    @Basic
    private boolean borrowed;
    
    @OneToOne
    private Product product;
    
    @Basic
    private BigDecimal singlePrice;
    
    public Item() {

    }
    
    public BigDecimal getSum(){

        if(product.getPrice() == null){
            return new BigDecimal(0.00);
        }

        if(singlePrice == null){
            return product.getPrice();
        }

        if(amount == null){
            amount = 1;
        }

        return singlePrice.multiply(new BigDecimal(amount)).setScale(2, RoundingMode.HALF_UP);
    }
   
    public Integer getAmount() {
        return this.amount;
    }
    
    public void setName(String name){
        product.getName();
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
   
    public Integer getItem_id() {
        return this.item_id;
    }
   
    public void setItem_product(Product item_product) {
        this.product = item_product;
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

        return product.getName();
    }

    public BigDecimal getSinglePrice() {
        return singlePrice;
    }

    public Product getItem_product() {
        return product;
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
        return Objects.equals(this.item_id, other.item_id);
    }

    @Override
    public String toString() {
        return "Item{" + "item_id=" + item_id + ", amount=" + amount + ", ordery=" + ordery + ", borrowed=" + borrowed + ", item_product=" + product + ", singlePrice=" + singlePrice + '}';
    }
}
