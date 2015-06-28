/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.controller;

import com.forall.laundry.model.Customer;
import com.forall.laundry.model.Item;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author jd
 */
@Named
@ViewScoped
public class FilterView implements Serializable{
    
    @PersistenceUnit
    private EntityManagerFactory emf;
    
    private List<Item> items;
    
    private List<Item> filteredItems;
    
    @ManagedProperty("#{customerItems}")
    private Customer customer;

    @PostConstruct
    public void init(){
        items = emf.createEntityManager()
                .createQuery("SELECT i FROM Item i WHERE i.customer.id = :id")
                .setParameter("id", customer.getId())
                .getResultList();
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<Item> getFilteredItems() {
        return filteredItems;
    }

    public void setFilteredItems(List<Item> filteredItems) {
        this.filteredItems = filteredItems;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}