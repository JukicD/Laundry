/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.EntityDAO;

import com.forall.laundry.model.Customer;
import com.forall.laundry.model.Item;
import com.forall.laundry.model.Ordery;
import java.util.List;

/**
 *
 * @author jd
 */
public interface CustomerDAO extends DAO<Customer>{
    
    Customer findByName(String name);
    
    List<Ordery> findOrdersByName(String name);
    
    Ordery findOrderbyId(int id);
    
    List<Customer> getAllCustomers();
    
    List<Item> getItems(Customer customer);
    
    Ordery getCurrentOrder(Customer customer);
    
}
