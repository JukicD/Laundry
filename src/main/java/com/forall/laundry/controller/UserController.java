/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.controller;

import com.forall.laundry.model.Customer;
import com.forall.laundry.model.Item;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedProperty;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.hibernate.Session;

/**
 *
 * @author jd
 */
@Named
@SessionScoped
public class UserController implements Serializable{
    
    @PersistenceUnit
    private EntityManagerFactory emf;
    
    @Resource
    private UserTransaction ut;
    
    @ManagedProperty(value = "{customer}")
    private Customer customer;
    
    @Inject
    private Item item;
    
    private String name;
    
    private List<Item> items;
    
    public String setCustomer(Customer c){
        customer = c;
        name = c.getName();
        updateList(c);
        
        return "/pages/customerMain.xhtml";
    }
    
    public Customer getCustomer(){
        return customer;
    }

    public List<Item> getItems() {
        return items;
    }

    public void addItem(){
        
        try {
            ut.begin();
            Session session = (Session)emf.createEntityManager()
                                            .getDelegate();  
            item.setCustomer(customer);
            item.setBorrowed(true);
            session.save(item);
            ut.commit();
            
            updateList(customer);
            
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            try {
                Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
                ut.rollback();
            } catch (IllegalStateException | SecurityException | SystemException ex1) {
                Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }finally{
            item.setName(null);
            item.setAmount(0);
        }
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    private void updateList(Customer c){
        items = emf.createEntityManager()
                    .createQuery("SELECT i FROM Item i WHERE i.customer.id = :id")
                    .setParameter("id", c.getId())
                    .getResultList();
    }
}
