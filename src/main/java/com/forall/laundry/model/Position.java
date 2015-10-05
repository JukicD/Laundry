package com.forall.laundry.model;

import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by jd on 9/21/15.
 */
@Entity
public class Position implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToMany(fetch = FetchType.EAGER)
    @Cascade(CascadeType.ALL)
    private List<History> history;

    @OneToOne
    private Product product;

    @Basic
    private Integer amount;

    @Basic
    private BigDecimal singlePrice;

    @Basic
    private String name;

    @Basic
    private BigDecimal sum;

    public Position(){
        amount = 0;
        history = new ArrayList<>();
    }

    public void add(final Worker worker, final Integer additional){
        amount += additional;
        final History h = new History();
        h.setAmount(additional);
        h.setWorker(worker);
        h.setDate(new Date());
        history.add(h);
    }

    public Worker getFirstWorker(){
        return history.get(0).getWorker();
    }

    public long getId() {
        return id;
    }

    public Integer getAmount() {
        return amount;
    }

    public List<History> getHistory() {
        return history;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getName(){
        return product.getName();
    }

    public BigDecimal getSinglePrice() {
        return singlePrice;
    }

    public void setSinglePrice(BigDecimal singlePrice) {
        this.singlePrice = singlePrice;
    }

    public BigDecimal getSum() {
        return singlePrice.multiply(new BigDecimal(amount));
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        String name = ((Position) o).getProduct().getName();
        BigDecimal price = ((Position) o).getSinglePrice();

        return product.getName().equals(name) && price.equals(singlePrice);

    }

    @Override
    public int hashCode() {
        int result = product.getName().hashCode();
        result = 31 * result + singlePrice.hashCode();
        return result;
    }


}
