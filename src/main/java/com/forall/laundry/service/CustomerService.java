/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.service;

import com.forall.laundry.EntityDAO.CustomerDAOImpl;
import com.forall.laundry.model.Customer;
import com.forall.laundry.model.Item;
import com.forall.laundry.model.Ordery;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author jd
 */
public class CustomerService implements Serializable{
    
    @Inject
    private CustomerDAOImpl dao;
    
    public void save(Customer customer){
        dao.saveOrUpdate(customer);
    }
    
    public Ordery getCurrentOrder(Customer customer){
        return dao.getCurrentOrder(customer);
    }
    
    public List<Customer> getAllCustomers(){
        return dao.getAllCustomers();
    }
    
    public Customer getByName(String name){
        return dao.findByName(name);
    }
    
    public void saveOrupdate(Customer customer){
        dao.saveOrUpdate(customer);
    }
    
    public List<Item> getItems(Customer customer){
        return dao.getItems(customer);
    }
}
