/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.controller.filter;

import com.forall.laundry.controller.OrderyController;
import com.forall.laundry.model.Ordery;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author jd
 */
@Named
@ViewScoped
public class OldOrdersFilter implements Serializable{
    
    @Inject
    private OrderyController orderyController;
    
    private String command;
    
    private String select;

    private Date dateFrom;
    
    private Date dateTo;
    
    private List<Ordery> orders;
    
    @PostConstruct
    public void init(){
        orders = orderyController.getThisMonthsOrders();
    }
    
    public void filter(){
        
        switch(command){
            case "all": orders = orderyController.getOldOrders(); break;
            case "recent": orders = orderyController.getThisMonthsOrders(); break;
            case "date": 
                                if(dateFrom != null && dateTo != null){
                                    orders = orderyController.getOrdersFrom(dateFrom, dateTo); break;
                                }
        }
    }
    
    public void filterOrders(SelectEvent event){
        
    }
    public void print(){
        System.out.println(command);
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getSelect() {
        return select;
    }

    public void setSelect(String select) {
        this.select = select;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public List<Ordery> getOrders() {
        return orders;
    }

    public void setOrders(List<Ordery> orders) {
        this.orders = orders;
    } 
}
