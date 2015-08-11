/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.view;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleModel;

/**
 *
 * @author jd
 */
@Named
@ViewScoped
public class ScheduleView implements Serializable{
    
    private ScheduleModel model;
    
    @PostConstruct
    public void init(){
        model = new DefaultScheduleModel();
    }
    
    public void onDateSelect(SelectEvent event){
        
    }
    
    public ScheduleModel getModel() {
        return model;
    }

    public void setModel(ScheduleModel model) {
        this.model = model;
    }
}
