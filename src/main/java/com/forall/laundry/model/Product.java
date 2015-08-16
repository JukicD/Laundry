package com.forall.laundry.model;


import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long product_id;
    
    @Basic
    @Column(columnDefinition="Decimal(10,2)")
    private BigDecimal price;

    @Basic
    private String name;

    public Product() {

    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
   
    public Long getProduct_id() {
        return this.product_id;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.product_id);
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
        final Product other = (Product) obj;
        return Objects.equals(this.product_id, other.product_id);
    }

    @Override
    public String toString() {
        return "Product{" + "product_id=" + product_id + ", price=" + price + ", name=" + name + '}';
    }
    
    
}
