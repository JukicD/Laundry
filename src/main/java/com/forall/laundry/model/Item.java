package com.forall.laundry.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 * Created by jd on 5/11/15.
 */

@Entity
public class Item implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne(targetEntity = Customer.class)
    private Customer customer;
    
    @NotNull
    private String name;

    private boolean borrowed;

    private Integer amount;

    public Item() {
        amount = 0;
    }

    public void setBorrowed(boolean type) {
        this.borrowed = type;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public boolean isBorrowed() {
        return borrowed;
    }

    public int getAmount() {
        return amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCustomer(Customer customers) {
        this.customer = customers;
    }
    
    
   
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;

        Item item = (Item) o;

        if (id != item.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }
}