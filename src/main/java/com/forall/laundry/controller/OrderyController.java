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
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
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
            orderyService.update(order);
            
            product.setName(null);
            item.setAmount(0);
            product.setPrice(null);
            editController.init();
    }
    
    public void finishOrder(){
        
        Ordery order = userController.getCurrentOrder();
        
        System.out.println("CUSTOMER: "+userController.getCustomer().getName());
        System.out.println("ORDER: " + order.getOrder_id());
        order.setDate(new Date());
        System.out.println("ORDER DATE: " +order.getDate());
        orderyService.update(order);
        Ordery o = new Ordery();
        o.setCustomer(userController.getCustomer());
        customerService.update(userController.getCustomer());
        orderyService.save(o);
    }
    
    public List<Ordery> getTodaysOrders(){
        
        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH)+1;
        int year = cal.get(Calendar.YEAR);
        
        return orderyService.getOrdersFrom(day, month, year);
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
}