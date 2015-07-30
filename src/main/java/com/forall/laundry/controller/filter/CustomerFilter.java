/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.controller.filter;


import com.forall.laundry.logger.AppLogger;
import com.forall.laundry.model.Customer;
import com.forall.laundry.service.CustomerService;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author jd
 */
@Named
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
        customers.stream().forEach(f -> System.out.println(f.getName()));
    }
    
    public boolean filterByName(Object value, Object filter, Locale locale){
        String filterText = (filter == null) ? null : filter.toString();
        logger.info("Filtertext: " + filterText);
        
        if(filterText == null || filterText.equals("")){
            return true;
        }
        
        if(value == null){
            return false;
        }
        
        return value.toString().compareTo(filterText) > 0;
    }
    
    public int sortByName(Object c1, Object c2){
       return ((Customer)c1).getName().compareTo(((Customer)c2).getName());
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
