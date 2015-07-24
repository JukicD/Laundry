/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.controller.selection;

import com.forall.laundry.controller.OrderyController;
import com.forall.laundry.model.Ordery;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

/**
 *
 * @author jd
 */
@Named
@ViewScoped
public class OrderySelectionController implements Serializable{
    
    @Inject
    OrderyController orderyController;
    
    private List<Ordery> selectedOrders;
    private List<Ordery> orders;
    
    @PostConstruct
    public void init(){
        orders = orderyController.getOldOrders();
    }
    public void onRowSelect(SelectEvent event) {
        
         System.out.println("ADD ORDER" + selectedOrders.size());
            
    }
 
    public void onRowUnselect(UnselectEvent event) {
        
        System.out.println("REMOVE ORDER");
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
