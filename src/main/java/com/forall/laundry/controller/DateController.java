/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.controller;

import java.util.Date;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author jd
 */

@Named
public class DateController {
    
    
    private Date date;
    
    public void onDateSelect(SelectEvent event){
        System.out.println("TEST");
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
