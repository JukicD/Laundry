/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.forall.laundry.controller;

import com.forall.laundry.controller.edit.EditController;
import com.forall.laundry.model.*;
import com.forall.laundry.service.*;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

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
    private Product product;
    
    @EJB
    private ProductService productService;
    
    @EJB
    private OrderyService orderyService;
    
    @EJB
    private CustomerService customerService;
    
    @EJB
    private BillingService billingService;

    @EJB
    private PositionService positionService;
    
    @Inject
    private EditController editController;

    public List<Ordery> getThisMonthsOrdersFrom(){
        return orderyService.getThisMonthsOrdersFrom(userController.getCustomer());
    }
    
    public List<Ordery> getOrders(){
        return orderyService.getOrdersFrom(userController.getCustomer());
    }
    
    public List<Ordery> getOldOrders(){
        return orderyService.getOldOrdersFrom(userController.getCustomer());
    }
    
    public List<Ordery> getOrdersFrom(Date from, Date to){
        
       return orderyService.getOrdersBetween(from, to, userController.getCustomer().getId());
    }
    
    public Product getProduct() {
        return product;
    }
    
    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Ordery> getAllOrdersFrom(final Customer customer) {

        return orderyService.getOrdersFrom(customer);
    }
}