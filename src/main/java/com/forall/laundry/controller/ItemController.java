/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.controller;

import com.forall.laundry.model.Customer;
import com.forall.laundry.model.Item;
import com.forall.laundry.model.Ordery;
import com.forall.laundry.model.Product;
import com.forall.laundry.service.ItemService;
import com.forall.laundry.service.OrderyService;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author jd
 */
@Named
@RequestScoped
public class ItemController implements Serializable{
    
    @Inject
    private UserController userController;
    
    @EJB
    private ItemService itemService;

    @EJB
    private OrderyService orderyService;
    
    @Inject
    private Item item;
    
    @Inject
    private Product product;
    
    public ItemController(){
        
    }
    
    public void addItem(){
        item.setProduct(product);
        itemService.save(item);
    }

    public List<Item> getTodaysItemsFrom(final Customer customer){

        final Date date = new Date();
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        final int day = cal.get(Calendar.DAY_OF_MONTH);
        final int month = cal.get(Calendar.MONTH) + 1;
        final int year = cal.get(Calendar.YEAR);

        final List<Ordery> orders = orderyService.getOrdersFromToday(day, month, year);

        return orders
                .parallelStream()
                .filter( o -> o.getCustomer().getId() == customer.getId())
                .flatMap( o -> o.getPositionMap().keySet().stream())
                .collect(Collectors.toList());
    }
    
    public UserController getUserController() {
        return userController;
    }

    public void setUserController(UserController userController) {
        this.userController = userController;
    }

    public ItemService getItemService() {
        return itemService;
    }

    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    
    
}
