/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.controller.filter;

import com.forall.laundry.model.Customer;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author jd
 */
@Named
@RequestScoped
public class ScheduleFilter implements Serializable{
    
    
    
    
    private List<Customer> customers;
    
    private Date date;

    
    public void onDateSelect(SelectEvent event){
        
        this.date = (Date) event.getObject();
        System.out.println(date);
    }
    
    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
