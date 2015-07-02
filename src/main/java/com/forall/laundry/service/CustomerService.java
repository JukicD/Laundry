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

 public class CustomerService implements EntityService<Customer, Item>, Serializable{

    @Inject
    private Customer customer;
     
    public CustomerService(){
        
    }
    
    @Override
    public void save() {
        final Session session = HibernateUtil.getSessionFactory().openSession();
        
        try{
            final Transaction tx = session.beginTransaction();
            try{
                session.save(customer);
                tx.commit();
            }catch (Exception e){
                tx.rollback();
                throw e;
            }
        }finally{
            session.close();
            customer.setName(null);
        }
    }

    @Override
    public Set<Customer> getAll() {
        final Session session = HibernateUtil.getSessionFactory().openSession();
        
        Set<Customer> resultSet = null;
        try{
            final Transaction tx = session.beginTransaction();
            
            try{
                resultSet = (Set<Customer>) session.createQuery("SELECT c FROM Customer c")
                                                   .list()
                                                   .stream()
                                                   .collect(Collectors.toSet());
            tx.commit();
            }catch (Exception e){
                tx.rollback();
                throw e;
            }
            
        }finally{
            session.close();
        }
        return resultSet;
    }

    @Override
    public Set<Item> getAllE() {
       final Session session = HibernateUtil.getSessionFactory().openSession();
        Set<Item> resultSet = null;
        try{
            final Transaction tx = session.beginTransaction();
            try{
                resultSet = (Set<Item>) session.createQuery("SELECT i From Item i WHERE i.customer.id = :id")
                                               .setParameter("id", customer.getId())
                                               .list()
                                               .stream()
                                               .collect(Collectors.toSet());
                tx.commit();
            }catch (Exception e){
                tx.rollback();
                throw e;
            }
        }finally{
            session.close();
        }
        return resultSet;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
