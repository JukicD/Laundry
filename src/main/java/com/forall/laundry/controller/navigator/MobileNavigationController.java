/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.controller.navigator;

import com.forall.laundry.model.Customer;
import com.forall.laundry.model.Worker;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author jd
 */
@Named
@SessionScoped
public class MobileNavigationController implements Serializable{
    
    @Inject
    private Worker worker;
    
    @Inject
    private Customer customer;

    public void setup(Worker worker){
        this.worker = worker;
    }
    
    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    public String gotoSecond(){
        return "pm:second";
    }
}
