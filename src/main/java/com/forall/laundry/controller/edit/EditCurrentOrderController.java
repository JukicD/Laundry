/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.controller.edit;

import com.forall.laundry.controller.UserController;
import com.forall.laundry.model.Item;
import com.forall.laundry.model.Product;
import com.forall.laundry.service.CustomerService;
import com.forall.laundry.service.ItemService;
import com.forall.laundry.service.ProductService;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author jd
 */

@Named
@ViewScoped
public class EditCurrentOrderController implements Serializable{
    
    private List<Item> items;
    
    @EJB
    private CustomerService customerService;
    
    @EJB
    private ItemService itemService;
    
    @EJB
    private ProductService productService;
    
    @Inject
    private UserController userController;
    
    @PostConstruct
    public void init(){
        items = customerService.getItems(userController.getCustomer());
    }
    
    public void onCellEdit(Item item){
         Item i = itemService.find(item.getItem_id());
        Product p = i.getItem_product();
        p.setName(item.getName());
        i.setItem_product(p);
        productService.update(p);
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public CustomerService getCustomerService() {
        return customerService;
    }

    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    public ItemService getItemService() {
        return itemService;
    }

    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
    }

    public ProductService getProductService() {
        return productService;
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public UserController getUserController() {
        return userController;
    }

    public void setUserController(UserController userController) {
        this.userController = userController;
    }
    
}
