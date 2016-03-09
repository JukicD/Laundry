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
import com.forall.laundry.view.TreeViewController;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.context.RequestContext;

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

    @Inject
    private TreeViewController treeViewController;

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
        
        byte[] pdf = billingService.createBill(orders, b.getBillNumber());
        
        System.out.println("PRINTING BILL: " + Arrays.toString(pdf));
        
        b.setBill(pdf);
        bilService.save(b);

        orders.forEach(order -> {
            order.setIsPrinted(true);
            orderyService.update(order);
        });

        Bill billInfo = billingService.getBill();
        billInfo.setNumber(billInfo.getNumber() + 1);
        billingService.update(billInfo);

        treeViewController.deleteSelectedNodes();
        treeViewController.resetOrders();
        RequestContext.getCurrentInstance().update(":oldOrdersTab:closedOrdersForm :oldOrdersTab:closedOrdersTableForm :oldOrdersTab:selectedPositionsTable");
    }

    public void printBillAgain(final Bil bil){
        billingService.createBill(bil.getOrders(), bil.getBillNumber());
    }

    public void save(){
        if(billingService.getBill() != null){
            billingService.update(bill);
        }else{
            billingService.save(bill);
        }
    }

    public void update(final Ordery order, final int id){
        Bil bil = bilService.get(order.getDate(), id);
        byte[] newBill = billingService.createBill(bil.getOrders(), bil.getBillNumber());
        bil.setBill(newBill);
        bilService.update(bil);
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }
}