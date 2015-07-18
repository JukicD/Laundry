/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.filters;


import com.forall.laundry.logger.AppLogger;
import com.forall.laundry.model.Customer;
import com.forall.laundry.service.CustomerService;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author jd
 */
@Named
@ViewScoped
public class CustomerFilter implements Serializable{
    
    @EJB
    private CustomerService cs;
    
    private List<Customer> customers;
    
    private List<Customer> filteredCustomers;
    
    @Inject
    private transient AppLogger logger;
    
    @PostConstruct
    public void init(){
        customers = cs.getAllCustomers();
    }
    
    public boolean filterByName(String value, String filter, Locale locale){
        
        logger.info(filter + " " + value);
        return value.equals(filter);
    }

    public CustomerService getCs() {
        return cs;
    }

    public void setCs(CustomerService cs) {
        this.cs = cs;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public List<Customer> getFilteredCustomers() {
        return filteredCustomers;
    }

    public void setFilteredCustomers(List<Customer> filteredCustomers) {
        this.filteredCustomers = filteredCustomers;
    }

    public AppLogger getLogger() {
        return logger;
    }

    public void setLogger(AppLogger logger) {
        this.logger = logger;
    }
    
    
    
    
}
