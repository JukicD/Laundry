package com.forall.laundry.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jd on 5/11/15.
 */

@Entity
public class Purchase implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    private Customer customer;

    @ManyToMany(cascade = CascadeType.ALL)
    private final Set<Worker> workers;

    public int getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Set<Item> getCustomersItems(){
        return this.getCustomer().getItems();
    }

    public void addWorker(Worker worker){
        this.workers.add(worker);
    }

    public Purchase(){
        this.workers = new HashSet<>();
    }

    public void setCustomer(Customer customer){
        this.customer = customer;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + this.id;
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
        final Purchase other = (Purchase) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
}
