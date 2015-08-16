/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.controller;

import com.forall.laundry.model.Item;
import com.forall.laundry.model.Product;
import com.forall.laundry.service.ItemService;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

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
    
    @Inject
    private Item item;
    
    @Inject
    private Product product;
    
    public ItemController(){
        
    }
    
    public void addItem(){
        item.setItem_product(product);
        item.setBorrowed(true);
        itemService.save(item);
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
