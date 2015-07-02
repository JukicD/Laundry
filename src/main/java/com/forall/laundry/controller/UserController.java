/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.controller;

import com.forall.laundry.model.Customer;
import com.forall.laundry.service.CustomerService;
import com.forall.laundry.service.ItemService;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author jd
 */
@Named
@SessionScoped
public class UserController implements Serializable{
   
        @Inject
        private CustomerService customerService;

        @Inject
        private ItemService itemService;

        public String setCustomer(Customer c, String nav){

            customerService.setCustomer(c);
            return nav;
        }

        public void addItem(){

           itemService.getItem().setBorrowed(true);
           itemService.getItem().setCustomer(customerService.getCustomer());
           itemService.save();

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
   }

    
    
    

