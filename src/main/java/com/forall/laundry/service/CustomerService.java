/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.service;

import com.forall.laundry.logger.AppLogger;
import com.forall.laundry.model.Customer;
import com.forall.laundry.model.Item;
import com.forall.laundry.model.Ordery;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author jd
 */
@Stateless
public class CustomerService {
    
        @PersistenceContext
        private EntityManager em;
        
        @Inject
        AppLogger logger;
        
        public List<Customer> getAllCustomers(){
            
            return em.createNamedQuery("Customer.findAll").getResultList();
        }
        
    public Customer findById(int id) {
        
        return em.find(Customer.class, id);
    }
    
    public List<Ordery> findOrdersById(int id) {
 
            return (List<Ordery>) em.createQuery("SELECT o FROM Ordery o WHERE o.customer.id = :id")
                                                        .setParameter("id", id)
                                                        .getSingleResult();
        
    }
    
    public void save(Customer customer){
        try{
            em.persist(customer);
            logger.info("CUSTOMER CREATED ! Name: " + customer.getName() + ", Address: " +customer.getAddress() + ", ID: " + customer.getId());
        }catch (Exception e){
            logger.error("FAILURE. Customer with ID: " + customer.getId() + "was not created !");
        } 
    }
    
    public void update(Customer customer){
        em.merge(customer);
    }
    

    public List<Item> getItems(Customer customer) {
        
      return em.createQuery("SELECT i FROM Item i, Ordery o WHERE o.customer.id = :id AND o.date IS NULL AND i.ordery.order_id = o.order_id")
                        .setParameter("id", customer.getId())
                        .getResultList();
        
    }

    public Ordery getCurrentOrder(Customer customer) {
      
            return (Ordery) em.createQuery("SELECT o FROM Ordery o WHERE o.date IS NULL AND o.customer.id = :id")
                    .setParameter("id", customer.getId())
                    .getSingleResult();
    }
}