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
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import net.sf.jasperreports.engine.JRException;

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
        bill = billingService.getBill(bill);
    }
    
    public void getBill(List<Ordery> orders, Customer customer){
        System.out.println("BILL");
        try {
            billingService.createBill(customer, orders);
        } catch (JRException ex) {
            Logger.getLogger(BillingController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void save(){
        billingService.save(bill);
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }
    
    
}
