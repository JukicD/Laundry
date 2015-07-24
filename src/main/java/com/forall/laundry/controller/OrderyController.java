/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.controller;

import com.forall.laundry.controller.edit.EditController;
import com.forall.laundry.model.Customer;
import com.forall.laundry.model.Item;
import com.forall.laundry.model.Ordery;
import com.forall.laundry.model.Product;
import com.forall.laundry.service.BillingService;
import com.forall.laundry.service.CustomerService;
import com.forall.laundry.service.ItemService;
import com.forall.laundry.service.OrderyService;
import com.forall.laundry.service.ProductService;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author jd
 */
@Named
@RequestScoped
public class OrderyController implements Serializable{
    
    @Inject
    private UserController userController;
    
    @Inject
    private Item item;
    
    @Inject
    private Product product;
    
    @EJB
    private ItemService itemService;
    
    @EJB 
    private ProductService productService;
    
    @EJB
    private OrderyService orderyService;
    
    @EJB
    private CustomerService customerService;
    
    @EJB
    private BillingService billingService;
    
    @Inject
    EditController editController;
    
    
    public void addItem(){
        
            Customer customer = userController.getCustomer();
            Ordery order = userController.getCurrentOrder();
            order.setCustomer(customer);
            customerService.update(customer);
            
            item.setOrdery(order);
            item.setItem_product(product);
            order.addItem(item);
            
            productService.save(product);
            itemService.save(item);
            orderyService.save(order);
            
            product.setName(null);
            item.setAmount(0);
            product.setPrice(null);
            editController.init();
    }
    
    public void finishOrder(){
        try {
            Ordery order = userController.getCurrentOrder();
            Customer customer = userController.getCustomer();
            order.setDate(new Date());
            
            orderyService.save(order);
            
            Ordery o = new Ordery();
            o.setCustomer(customer);
         
            customerService.update(customer);
            orderyService.save(o);
            
            FacesContext.getCurrentInstance().getExternalContext().redirect("/pages/customerList.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(OrderyController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Ordery> getOrders(){
        return orderyService.getOrdersFrom(userController.getCustomer());
    }
    
    public List<Ordery> getOldOrders(){
        return orderyService.getOldOrdersFrom(userController.getCustomer());
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

    public OrderyService getOrderyService() {
        return orderyService;
    }

    public void setOrderyService(OrderyService orderyService) {
        this.orderyService = orderyService;
    }

    public CustomerService getCustomerService() {
        return customerService;
    }

    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }
}
