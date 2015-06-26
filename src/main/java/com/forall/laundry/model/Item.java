package com.forall.laundry.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 * Created by jd on 5/11/15.
 */

@Entity
public class Item implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToMany(mappedBy = "items")
    private final Set<Customer> customers;

    private boolean borrowed;

    private int amount;

    public Item() {

        this.customers = new HashSet<>();
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

    public Set<Customer> getCustomers() {
        return customers;
    }

    public boolean isBorrowed() {
        return borrowed;
    }

    public int getAmount() {
        return amount;
    }

    public void addCustomer(Customer customer){
        this.customers.add(customer);
    }

    public void removeCustomer(Customer customer){
        this.customers.add(customer);
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