/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.service;

import com.forall.laundry.logger.AppLogger;
import com.forall.laundry.model.*;
import org.primefaces.context.RequestContext;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
                                                        .getResultList();
        
    }
    
    public void save(Customer customer){
        try{
            em.persist(customer);
            RequestContext.getCurrentInstance().update("customerPanel");
            logger.info("CUSTOMER CREATED ! Name: " + customer.getName() + ", Address: " +customer.getAddress() + ", ID: " + customer.getId());
        }catch (Exception e){
            logger.error("FAILURE. Customer with ID: " + customer.getId() + "was not created ! Cause: " + e.getCause());
        } 
    }
    
    public void update(Customer customer){
        em.merge(customer);
        logger.info("Customer successfully updated! " + customer.toString());
    }
    

    public List<Item> getItems(Customer customer) {
        
      return em.createQuery("SELECT i FROM Item i, Ordery o  WHERE o.customer.id = :id AND o.date IS NULL AND i.ordery.id = o.id")
                        .setParameter("id", customer.getId())
                        .getResultList();
    }

    public Ordery getCurrentOrder(Customer customer) {
      
            return (Ordery) em.createQuery("SELECT o FROM Ordery o WHERE o.date IS NULL AND o.customer.id = :id")
                    .setParameter("id", customer.getId())
                    .getSingleResult();
    }
    
    public Customer findByName(String name){
        
        List<Customer> customers = em.createQuery("SELECT c FROM Customer c WHERE c.name = :name").setParameter("name", name).getResultList();

        return customers.get(0);
    }
    
    public List<Customer> getCustomersFrom(final Date from, final Date to){
        
        final Calendar fromCal = Calendar.getInstance();
        fromCal.setTime(from);
        
        int day = fromCal.get(Calendar.DAY_OF_MONTH);
        int month = fromCal.get(Calendar.MONTH) + 1;
        int year = fromCal.get(Calendar.YEAR);

        final Calendar toCal = Calendar.getInstance();
        toCal.setTime(to);



        logger.info("Searching Customers from " + day +"."+month+"."+year);
        
        List<Customer> customers =  em
                .createQuery("SELECT DISTINCT c FROM Ordery o, Customer c "
                        + "WHERE o.customer.id = c.id "
                        + "AND EXTRACT(DAY FROM time) = :day "
                        + "AND EXTRACT(MONTH FROM time) = :month "
                        + "AND EXTRACT(YEAR FROM time) = :year")
                .setParameter("day", day)
                .setParameter("month", month)
                .setParameter("year", year)
                .getResultList();

        logger.info("Found " + customers.size() + " Customers!");
        return customers;
    }

    public List<Product> getProductsFrom(final Customer customer){

        return customer.getPropertyMap().keySet().stream().collect(Collectors.toList());
    }

    public List<Customer> getTodaysCustomers() {

        final Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        final int day = cal.get(Calendar.DAY_OF_MONTH);
        final int month = cal.get(Calendar.MONTH) + 1;
        final int year = cal.get(Calendar.YEAR);

        List<Customer> customers = em.createQuery("SELECT DISTINCT c FROM Customer c, Ordery o WHERE EXTRACT(DAY FROM time) = :day AND EXTRACT(MONTH FROM time) = :month AND EXTRACT(YEAR FROM time) = :year AND o.customer.id = c.id ORDER BY c.name ASC ")
                .setParameter("day", day)
                .setParameter("month", month)
                .setParameter("year", year)
                .getResultList();

        return customers;
    }
}
