/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.controller.selection;

import com.forall.laundry.controller.OrderyController;
import com.forall.laundry.logger.AppLogger;
import com.forall.laundry.model.Ordery;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jd
 */
@Named
@RequestScoped
public class OrderySelectionController implements Serializable{
    
    @Inject
    private OrderyController orderyController;
    
    @Inject
    private AppLogger logger;
    
    private List<Ordery> selectedOrders;
    private List<Ordery> orders;
    
    @PostConstruct
    public void init(){
        orders = orderyController.getOldOrders();
        logger.info("Selected Orders: " + orders);
        
    }
    public void onRowSelect(SelectEvent event) {
            
    }
    
    public void reset(){
        selectedOrders = new ArrayList<>();
        RequestContext.getCurrentInstance().update(":oldOrderList:orderTable");
    }
    
    public void onRowUnselect(UnselectEvent event) {
    
    }

    public List<Ordery> getSelectedOrders() {
        return selectedOrders;
    }

    public void setSelectedOrders(List<Ordery> selectedOrders) {
        this.selectedOrders = selectedOrders;
    }

    public List<Ordery> getOrders() {
        return orders;
    }

    public void setOrders(List<Ordery> orders) {
        this.orders = orders;
    }
}
