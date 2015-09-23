package com.forall.laundry.model;

import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
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

    @Basic
    private Integer amount;

    @Basic
    private BigDecimal sum;

    @OneToOne(fetch = FetchType.EAGER)
    private Worker worker;

    public Position(){
        amount = 0;
        sum = new BigDecimal(0);
        history = new ArrayList<>();
    }

    public void add(final Item item){

        amount += item.getAmount();

        final History h = new History();
        h.setAmount(item.getAmount());
        h.setWorker(item.getWorker());
        history.add(h);


        final int newSum = sum.intValue() + item.getSinglePrice().intValue() * item.getAmount();
        sum = null;
        sum = new BigDecimal(newSum);
        worker = item.getWorker();
    }

    public long getId() {
        return id;
    }

    public Integer getAmount() {
        return amount;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public List<History> getHistory() {
        return history;
    }
}
