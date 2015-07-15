/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.service;

import com.forall.laundry.model.Customer;
import com.forall.laundry.model.Item;
import com.forall.laundry.model.Ordery;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author jd
 */
@Stateless
public class OrderyService {

    @PersistenceContext
    EntityManager em;
    
    public List<Item> getItemsByOrderID(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    public List<Item> getItemsFromCurrentOrder(Customer customer) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }   

    public List<Ordery> getOrdersFrom(Customer customer) {
       
        return em.createNamedQuery("Ordery.findCustomersOrders")
                            .setParameter("id", customer.getId())
                            .getResultList();
    }

    public List<Ordery> getOldOrdersFrom(Customer customer) {
        
        return em.createNamedQuery("Ordery.findCustomersOldOrders")
                            .setParameter("id", customer.getId())
                            .getResultList();
    }
    
    public void save(Ordery o){
        em.merge(o);
    }
    
    public void update(Ordery o){
        em.merge(o);
    }
}
