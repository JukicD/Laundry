/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.controller;

import com.forall.laundry.model.Bill;
import com.forall.laundry.model.BillSetting;
import com.forall.laundry.model.Customer;
import com.forall.laundry.model.Ordery;
import com.forall.laundry.service.BillService;
import com.forall.laundry.service.BillPrintingService;
import com.forall.laundry.service.CustomerService;
import com.forall.laundry.service.OrderyService;
import com.forall.laundry.service.PositionService;
import com.forall.laundry.view.TreeViewController;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
    private BillPrintingService billingService;

    @EJB
    private OrderyService orderyService;

    @EJB
    private BillService billService;
    
    @EJB
    private PositionService positionService;
    
    @EJB
    private CustomerService customerService;

    @Inject
    private TreeViewController treeViewController;

    @Inject
    private BillSetting bill;

    @PostConstruct
    public void init(){
        this.bill = billingService.getBill() != null ? billingService.getBill() : new BillSetting();
    }

    public void printBill(final List<Ordery> orders, final Customer customer){
        assert(!orders.isEmpty());
        
        Customer cus = customerService.findById(customer.getId());
        
        Bill b = new Bill();
        b.setBillNumber(billingService.getBillNumber());
        b.setPrinted(new Date());
        
        
        byte[] pdf = billingService.createBill(orders, b.getBillNumber());
        
        orders.stream().forEach(order -> {
            orderyService.delete(order);
            order.getPositions().stream().forEach(position -> {
                positionService.delete(position.getId());
            });
        });
        b.setBill(pdf);
        billService.save(b);
        cus.add(b);
        customerService.update(cus);

        BillSetting billInfo = billingService.getBill();
        billInfo.setNumber(billInfo.getNumber() + 1);
        billingService.update(billInfo);

        treeViewController.deleteSelectedNodes();
        treeViewController.resetOrders();
        RequestContext.getCurrentInstance().update(":oldOrdersTab:closedOrdersForm :oldOrdersTab:closedOrdersTableForm :oldOrdersTab:selectedPositionsTable");
    }

    public void save() {
        try {
            if (billingService.getBill() != null) {
                billingService.update(bill);
            } else {
                billingService.save(bill);
            }
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Die Rechnungseinstellungen wurden erfolgreich geändert",""));
        }catch (Exception e){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Die Rechnungseinstellungen konnten nicht geändert werden!",""));
        }

        bill = null;
    }
    
    public BillSetting getBill() {
        return bill;
    }

    public void setBill(BillSetting bill) {
        this.bill = bill;
    }
}