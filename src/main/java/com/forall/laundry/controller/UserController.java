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
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
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
    
    @EJB
    private CustomerService customerService;
    
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

    public void setup(Customer customer, String nav){
        this.customer = customer;
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(nav);
        } catch (IOException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void update(){
        customerService.update(customer);
    }
}
