/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.controller;

import com.forall.laundry.model.Customer;
import com.forall.laundry.model.Item;
import com.forall.laundry.model.Ordery;
import com.forall.laundry.model.Product;
import com.forall.laundry.service.CustomerService;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author jd
 */
@Named
@SessionScoped
public class UserController implements Serializable{
    
    @Inject
    private Customer customer;
    
    @EJB
    private CustomerService customerService;
    
    public UserController(){
        
    }

    public List<Product> getProducts(){
        return customerService.getProductsFrom(customer);
    }
    public List<Item> getItems(){
        return customerService.getItems(customer);
    }
    
    public Ordery getCurrentOrder(){
        return customerService.getCurrentOrder(customer);
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public CustomerService getCustomerService() {
        return customerService;
    }

    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    public void setup(Customer customer){
        this.customer = customer;
    }
    
    public void update(){
        customerService.update(customer);
    }
}
