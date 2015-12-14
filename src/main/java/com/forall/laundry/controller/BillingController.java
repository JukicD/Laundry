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
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.Asynchronous;
import javax.ejb.Stateful;

/**
 *
 * @author jd
 */
@Named
@Stateful
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
    
    @Inject
    private UserController userController;
    
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
        b.setBill(billingService.createBill(orders));
        bilService.save(b);

        orders.forEach(order -> {
            order.setIsPrinted(true);
            orderyService.update(order);
        });

        Bill billInfo = billingService.getBill();
        billInfo.setNumber(billInfo.getNumber() + 1);
        billingService.update(billInfo);
        treeViewSingleton.init();
        RequestContext.getCurrentInstance().update("customerPanel");
        
    }

    public void printBillAgain(final Bil bil){
        billingService.createBill(bil.getOrders());
    }
    
    public void printBillAgain(final Ordery order){
        Bil bil = bilService.get(order.getDate(), userController.getCustomer().getId());
        billingService.createBill(bil.getOrders());
    }
    
    public void save(){
        if(billingService.getBill() != null){
            billingService.update(bill);
        }else{
            billingService.save(bill);
        }
    }
    
    @Asynchronous
    public void update(final Ordery order, final int id){
        Bil bil = bilService.get(order.getDate(), id);
        bil.setBill(billingService.createBill(bil.getOrders()));
        bilService.update(bil);
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }
}
