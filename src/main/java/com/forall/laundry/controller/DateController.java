/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.controller;

import org.primefaces.event.SelectEvent;

import javax.inject.Named;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author jd
 */
@Named
public class DateController implements Serializable{
    
    public void update(SelectEvent event){
        
        Date date = (Date) event.getObject();
    }
    
}
