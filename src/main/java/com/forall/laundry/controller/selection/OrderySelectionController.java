/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.controller.selection;

import com.forall.laundry.model.Ordery;
import java.io.Serializable;
import java.util.List;
import javax.faces.view.ViewScoped;
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
    
    private List<Ordery> selectedOrders;
    
    public void onRowSelect(SelectEvent event) {
        Ordery ordery = (Ordery) event.getObject();
        selectedOrders.add(ordery);
    }
 
    public void onRowUnselect(UnselectEvent event) {
        Ordery ordery = (Ordery) event.getObject();
        selectedOrders.remove(ordery);
    }

    public List<Ordery> getSelectedOrders() {
        return selectedOrders;
    }

    public void setSelectedOrders(List<Ordery> selectedOrders) {
        this.selectedOrders = selectedOrders;
    }
}
