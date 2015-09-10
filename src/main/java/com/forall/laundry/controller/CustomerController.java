/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.controller;

import com.forall.laundry.logger.AppLogger;
import com.forall.laundry.mobile.CustomerAutoCompleteFilter;
import com.forall.laundry.mobile.MobileCustomerController;
import com.forall.laundry.model.Customer;
import com.forall.laundry.model.Ordery;
import com.forall.laundry.service.CustomerService;
import com.forall.laundry.service.OrderyService;
import org.primefaces.context.RequestContext;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author jd
 */
@Named
@RequestScoped
public class CustomerController implements Serializable{
    
   @EJB
   private CustomerService customerService;
   
   @EJB
   private OrderyService orderyService;
   
   @Inject
   private Customer customer;

    @Inject
    private CustomerAutoCompleteFilter customerAutoCompleteFilter;

   @Inject
   private AppLogger logger;

   public void createCustomer(){
       
       try{
           
        Ordery ordery = new Ordery();
           ordery.setDate(new Date());
        ordery.setCustomer(customer);
        
        customerService.save(customer);
        orderyService.save(ordery);
        
        logger.info("CUSTOMER CREATED: " +customer.toString());
        
       }catch (Exception e){
           logger.error("FAILURE SAVING: " +customer.toString() + " Cause: " + e.getCause());
       }finally{
            customer.setAddress(null);
            customer.setCompanyName(null);
            customer.setPhoneNumber(null);
            customer.setZipCode(null);
            customer.setCustomerNumber(0);
            customer.setMailAddress(null);
            customer.setName(null);
       }
        customerAutoCompleteFilter.init();
   }
   
   public void update(){
       try{
           customerService.update(customer);
           logger.info("Customer " + customer.getName() + " was updated!");
       }catch (Exception e){
           logger.error("Update Failed ! " + customer.getName());
       }
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

    public AppLogger getLogger() {
        return logger;
    }

    public void setLogger(AppLogger logger) {
        this.logger = logger;
    }
}
