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
import com.forall.laundry.service.OrderyService;
import org.omnifaces.config.FacesConfigXml;
import org.primefaces.context.RequestContext;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
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
@RequestScoped
public class BillingController implements Serializable{
    
    @EJB
    private BillingService billingService;

    @EJB
    private OrderyService orderyService;
    
    @Inject
    private Bill bill;
    
    @PostConstruct
    public void init(){
        this.bill = billingService.getBill() != null ? billingService.getBill() : new Bill();
    }

    public void printBill(List<Ordery> orders, Customer customer){
        System.out.println(orders);
        assert(!orders.isEmpty());

        orders.forEach(o -> {
            o.setIsPrinted(true);
            orderyService.update(o);
        });

        billingService.createBill(customer, orders);

        if(!orders.isEmpty()){
            incrementBillNumber();
        }

        RequestContext.getCurrentInstance().update("customerPanel");
    }
    
    public void save(){
        if(billingService.getBill() != null){
            billingService.update(bill);
        }else{
            billingService.save(bill);
        }
    }

    private void incrementBillNumber(){

        Long number = bill.getNumber();
        number++;
        bill.setNumber(number);

        save();
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }
}
