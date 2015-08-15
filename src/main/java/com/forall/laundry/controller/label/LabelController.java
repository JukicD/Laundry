/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.controller.label;

import com.forall.laundry.controller.UserController;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author jd
 */
@Named
@ViewScoped
public class LabelController implements Serializable{
    
    @Inject
     private UserController userController;
    
    String label;

    
    @PostConstruct
    public void init(){
        label = "Kundenliste";
    }
    
    public void description(String description){
        this.label = description + " " + userController.getCustomer().getName();
    }
    
    public void label(String label){
        this.label = label;
    }
    
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
