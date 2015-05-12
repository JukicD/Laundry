package com.forall.laundry.model;

import javax.annotation.Generated;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by jd on 5/11/15.
 */
@Entity
public class Worker implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String surname;

    private String name;

    @ManyToMany(mappedBy = "workers", cascade = {CascadeType.ALL})
    private Set<Purchase> purchases;

    public int getId() {
        return id;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public Set<Purchase> getOrders() {
        return purchases;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setName(String name) {
        this.name = name;
    }
}
