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
    private Set<Worker> workers;

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
}
