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
public interface OrderyDAO extends DAO<Ordery>{
    
    void addItem(Customer customer, Item item);
    
    List<Item> getItemsByOrderID(int id);
    
    List<Item> getItemsFromCurrentOrder(Customer customer);
    
}
