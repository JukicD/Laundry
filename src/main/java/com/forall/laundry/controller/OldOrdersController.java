/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.controller;

import com.forall.laundry.model.Item;
import com.forall.laundry.model.Ordery;
import com.forall.laundry.service.ItemService;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author jd
 */
@Named
@SessionScoped
public class OldOrdersController implements Serializable{
    
    @Inject
    private Ordery order;
    
    @Inject
    private ItemService itemService;

    public List<Item> getItems(){
        return itemService.getItemsFrom(order);
    }
    
    public String setup(Ordery order){
        this.order = order;
        return "/pages/orders.xhtml";
    }
    public Ordery getOrder() {
        return order;
    }

    public void setOrder(Ordery order) {
        this.order = order;
    }
    
    
    
}
