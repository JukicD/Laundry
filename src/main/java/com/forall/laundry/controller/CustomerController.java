/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.controller;

import com.forall.laundry.model.Customer;
import com.forall.laundry.model.Ordery;
import com.forall.laundry.service.CustomerService;
import com.forall.laundry.service.OrderyService;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author jd
 */
@Named
@RequestScoped
public class CustomerController implements Serializable{
    
   @Inject
   private CustomerService customerService;
   
   @Inject
   private OrderyService orderyService;
   
   @Inject
   private Customer customer;

   public void createCustomer(){
        Ordery ordery = new Ordery();
        
        ordery.setCustomer(customer);
        customerService.save(customer);
        orderyService.saveOrUpdate(ordery);
        
        customer.setName(null);
        customer.setAddress(null);
   }
   
   public void saveOrupdate(){
       customerService.saveOrupdate(customer);
   }
   
   public List<Customer> getAllCustomers(){
       return customerService.getAllCustomers();
   }
    public CustomerService getCustomerService() {
        return customerService;
    }

    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    public Customer getCustomer() {
        return customer;
    }
    
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public OrderyService getOrderyService() {
        return orderyService;
    }

    public void setOrderyService(OrderyService orderyService) {
        this.orderyService = orderyService;
    }
}
