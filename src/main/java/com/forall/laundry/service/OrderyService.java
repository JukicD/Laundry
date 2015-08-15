/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.service;

import com.forall.laundry.model.Customer;
import com.forall.laundry.model.Item;
import com.forall.laundry.model.Ordery;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
    
    public List<Ordery> getOrdersFromToday(int day, int month, int year){
        
        List<Ordery> orders = em.createQuery("SELECT o From Ordery o "
                                                                    + "WHERE EXTRACT(DAY FROM time) = :day "
                                                                        + "AND EXTRACT(MONTH FROM time) = :month "
                                                                        + "AND EXTRACT(YEAR FROM time) = :year")
                                                                     .setParameter("day", day)
                                                                     .setParameter("month", month)
                                                                     .setParameter("year", year)
                                                                     .getResultList();
        return orders;
                
    }
    
    public List<Ordery> getOrdersFromThisWeek(List<Calendar> daysOfWeek){
        List<Ordery> weeksOrders = new ArrayList<>();
        daysOfWeek
                .parallelStream()
                .forEach( cal-> {
                    int day = cal.get(Calendar.DAY_OF_MONTH);
                    int month = cal.get(Calendar.MONTH) + 1;
                    int year = cal.get(Calendar.YEAR);
                    
                    List<Ordery> orders = null;
                    try{
                        orders = em.createQuery("SELECT o From Ordery o "
                                                                    + "WHERE EXTRACT(DAY FROM time) = :day "
                                                                    + "AND EXTRACT(MONTH FROM time) = :month "
                                                                    + "AND EXTRACT(YEAR FROM time) = :year")
                                            .setParameter("day", day)
                                            .setParameter("month", month)
                                            .setParameter("year", year)
                                            .getResultList();
                   }catch (NoResultException e){ 
                        
                   }
                   
                    weeksOrders.addAll(orders);
                });

        return weeksOrders;
    }
    
    public List<Ordery> getOrdersFromMonth(int month, int year){
        
        List<Ordery> orders = em.createQuery("SELECT o From Ordery o "
                                                    + "WHERE EXTRACT(MONTH FROM time) = :month "
                                                    + "AND EXTRACT(YEAR FROM time) = :year")
                            .setParameter("month", month)
                            .setParameter("year", year)
                            .getResultList();
        return orders;
    }
    
    public List<Ordery> getOrdersBetween(Date from, Date to, int customerID){
        
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(format.format(from) + " " + format.format(to));
        return em.createQuery("SELECT o FROM Ordery o WHERE o.customer.id = :id AND o.date BETWEEN :from AND :to")
                                .setParameter("id", customerID)
                                .setParameter("from", from)
                                .setParameter("to", to)
                                .getResultList();
    }
    public void save(Ordery o){
        em.merge(o);
    }
    
    public void update(Ordery o){
        em.merge(o);
    }
}
