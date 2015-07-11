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
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author jd
 */
public class OrderyDAOImpl extends AbstractDAO<Ordery> implements OrderyDAO{

    @Override
    public void addItem(Customer customer, Item item) {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        
        
    }

    @Override
    public List<Item> getItemsByOrderID(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Item> getItemsFromCurrentOrder(Customer customer) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }   

    public List<Ordery> getOrdersFrom(Customer customer) {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        List<Ordery> result;
        try{
           result = session.createQuery("SELECT o FROM Ordery o WHERE o.customer.id = :id").setParameter("id", customer.getId()).list();
           session.flush();
           tx.commit();
           
        }catch(Exception e){
            tx.rollback();
            throw e;
        }
        return result;
    }

    public List<Ordery> getOldOrdersFrom(Customer customer) {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        List<Ordery> result;
        try{
           result = session.createQuery("SELECT o FROM Ordery o WHERE o.customer.id = :id AND o.date IS NOT NULL").setParameter("id", customer.getId()).list();
           session.flush();
           tx.commit();
           
        }catch(Exception e){
            tx.rollback();
            throw e;
        }
        return result;
    }
}
