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
public class CustomerDAOImpl extends AbstractDAO<Customer> implements CustomerDAO{
    
    @Override
    public Customer findByName(String name) {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        Customer customer;
        try {
           customer =(Customer) session.createQuery("SELECT c FROM Customer c WHERE c.name = :name")
                                        .setParameter("name", name)
                                        .uniqueResult();
           session.flush();
           tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }finally{
            session.close();
        }
        return customer;
    }

    @Override
    public List<Ordery> findOrdersByName(String name) {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        List<Ordery> result;
        try {
            result = session.createQuery("SELECT Ordery o FROM Customer c WHERE c.name = :name")
                            .setParameter("name", name)
                            .list();
            
            session.flush();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }finally{
            session.close();
        }
        return result;
    }

    @Override
    public Ordery findOrderbyId(int id) {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        Ordery result;
        try {
           result = (Ordery) session.createQuery("SELECT Ordery o FROM Customer c WHERE c.orderys.order_id = :id")
                                    .setParameter("id", id)
                                    .uniqueResult();
            
            session.flush();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }finally{
            session.close();
        }
        
        return result;
    }

    @Override
    public List<Customer> getAllCustomers() {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        List<Customer> result;
        try {
           result = session.createQuery("SELECT c FROM Customer c").list();
           session.flush();
           tx.commit();
        } catch (Exception e) {
            tx.commit();
            throw e;
        }finally{
            session.close();
        }
        return result;
    }

    @Override
    public List<Item> getItems(Customer customer) {
        
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        List<Item> result;
        
        try{
            result = session.createQuery("SELECT i FROM Item i, Ordery o WHERE o.customer.id = :id AND o.date IS NULL AND i.ordery.order_id = o.order_id")
                                        .setParameter("id", customer.getId())
                                        .list();
            session.flush();
            tx.commit();
        }catch (Exception e){
            tx.rollback();
            throw e;
        }
        
        return result;
    }

    @Override
    public Ordery getCurrentOrder(Customer customer) {
        System.out.println("Customer" + customer.getId());
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        Ordery result;
        try{
            result = (Ordery) session.createQuery("SELECT o FROM Ordery o WHERE o.date IS NULL AND o.customer.id = :id").setParameter("id", customer.getId()).uniqueResult();
            session.flush();
            tx.commit();
        }catch (Exception e){
            tx.rollback();
            throw e;
        }
        
        session.close();
        
        return result;
    }
}
