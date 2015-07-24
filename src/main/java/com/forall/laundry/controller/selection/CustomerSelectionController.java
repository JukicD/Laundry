/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.controller.selection;

import com.forall.laundry.model.Customer;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author jd
 */
@Named
@SessionScoped
public class CustomerSelectionController implements Serializable{
    
    private Customer selectedCustomer;
    
    public void deleteCustomer() {
        
        selectedCustomer = null;
    }

    public Customer getSelectedCustomer() {
        return selectedCustomer;
    }

    public void setSelectedCustomer(Customer selectedCustomer) {
        this.selectedCustomer = selectedCustomer;
    }
}
