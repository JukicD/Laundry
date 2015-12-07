/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.controller;

import com.forall.laundry.model.Bil;
import com.forall.laundry.model.Bill;
import com.forall.laundry.model.Customer;
import com.forall.laundry.model.Ordery;
import com.forall.laundry.service.BilService;
import com.forall.laundry.service.BillingService;
import com.forall.laundry.service.OrderyService;
import org.primefaces.context.RequestContext;
import singletons.TreeViewSingleton;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.Serializable;
import java.util.Date;
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

    @EJB
    private BilService bilService;

    @EJB
    private TreeViewSingleton treeViewSingleton;
    
    @Inject
    private Bill bill;
    
    @PostConstruct
    public void init(){
        this.bill = billingService.getBill() != null ? billingService.getBill() : new Bill();
    }

    public void printBill(final List<Ordery> orders, final Customer customer){
        assert(!orders.isEmpty());

            Bil b = new Bil();
            b.setBillNumber(billingService.getBillNumber());
            b.setPrinted(new Date());
            b.setCustomer(customer);
            b.setOrders(orders);

            bilService.save(b);

            orders.forEach(o -> {
                o.setIsPrinted(true);
                orderyService.update(o);
            });

            if(!orders.isEmpty()) {
                incrementBillNumber();
            }

        billingService.createBill(orders);

        RequestContext.getCurrentInstance().update("customerPanel");
        treeViewSingleton.init();
    }

    public void printBillAgain(final Bil bil){
        billingService.createBill(bil.getOrders());
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
