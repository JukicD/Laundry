/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.controller;

import com.forall.laundry.model.Customer;
import com.forall.laundry.model.Item;
import com.forall.laundry.model.Ordery;
import com.forall.laundry.service.CustomerService;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author jd
 */
@Named
@SessionScoped
public class UserController implements Serializable{
    
    @Inject
    private Customer customer;
    
    @Inject
    private Ordery order;
    
    @Inject
    private CustomerService customerService;
    
    @PostConstruct
    public void init(){
        order = customerService.getCurrentOrder(customer);
    }
    public UserController(){
        
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

    public String setup(Customer customer, String nav){
        this.customer = customer;
        return nav;
    }
    
    public void update(){
        customerService.saveOrupdate(customer);
        customer.setName(null);
        customer.setAddress(null);
    }

    public Ordery getOrder() {
        return order;
    }

    public void setOrder(Ordery order) {
        this.order = order;
    }
}
