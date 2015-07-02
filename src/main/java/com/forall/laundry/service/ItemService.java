/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.service;

import com.forall.laundry.hibernate.HibernateUtil;
import com.forall.laundry.model.Customer;
import com.forall.laundry.model.Item;
import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;
import javax.inject.Inject;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author jd
 */
public class ItemService implements EntityService<Item, Customer>, Serializable{

    @Inject
    private Item item;
    
    public ItemService(){
        
    }
    
    @Override
    public void save() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        
        try{
            Transaction tx = session.beginTransaction();
            try{
                session.save(item);
                tx.commit();
            }catch (Exception e){
                tx.rollback();
                throw e;
            }
        }finally{
            session.close();
            item.setName(null);
            item.setAmount(0);
        }
    }

    @Override
    public Set<Item> getAll() {
        final Session session = HibernateUtil.getSessionFactory().openSession();
        Set<Item> resultSet = null;
        
        try{
            final Transaction tx = session.beginTransaction();
            try{
                resultSet = (Set<Item>) session.createQuery("SELECT i FROM Item i").list().stream().collect(Collectors.toSet());
                tx.commit();
            }catch(Exception e){
                tx.rollback();
                throw e;
            }
        }finally{
            session.close();
        }
        
        return resultSet;
    }

    @Override
    public <E> E getAllE() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
    
    
    
}
