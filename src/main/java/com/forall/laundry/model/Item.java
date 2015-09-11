package com.forall.laundry.model;


import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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

    @OneToOne
    private Worker worker;
    
    @OneToOne
    private Product product;
    
    @Basic
    private BigDecimal singlePrice;
   
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

    public void setSinglePrice(BigDecimal singlePrice) {
        this.singlePrice = singlePrice;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
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
        return "Item{" + "item_id=" + item_id + ", amount=" + amount + ", ordery=" + ordery + ", product=" + product + ", singlePrice=" + singlePrice + '}';
    }
}
