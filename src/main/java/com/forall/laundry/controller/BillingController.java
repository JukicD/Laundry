/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.controller;

import com.forall.laundry.model.Bill;
import com.forall.laundry.model.Customer;
import com.forall.laundry.model.Ordery;
import com.forall.laundry.service.BillingService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author jd
 */
@Named
@ViewScoped
public class BillingController implements Serializable{
    
    @EJB
    private BillingService billingService;
    
    @Inject
    private Bill bill;
    
    @PostConstruct
    public void init(){
        this.bill = billingService.getBill() != null ? billingService.getBill() : new Bill();
    }
    public void getBill(List<Ordery> orders, Customer customer){
        billingService.createBill(customer, orders);
    }
    
    public void save(){
        if(billingService.getBill() != null){
            billingService.update(bill);
        }else{
            billingService.save(bill);
        }
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }
}
