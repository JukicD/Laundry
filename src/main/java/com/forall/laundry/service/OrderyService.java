/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.service;

import com.forall.laundry.model.Customer;
import com.forall.laundry.model.Item;
import com.forall.laundry.model.Ordery;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.spi.CalendarNameProvider;

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
    
    public List<Ordery> getOrdersBetween(final Date from, Date to, final int customerID){
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(to);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        to = cal.getTime();

        return em.createQuery("SELECT o FROM Ordery o WHERE o.customer.id = :id AND o.date BETWEEN :from AND :to")
                                .setParameter("id", customerID)
                                .setParameter("from", from)
                                .setParameter("to", to)
                                .getResultList();
    }

    public List<Ordery> getThisMonthsOrdersFrom(Customer customer) {

        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        cal.set(Calendar.HOUR_OF_DAY, 0);

        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.MILLISECOND);
        cal.add(Calendar.DAY_OF_MONTH, 1);

        today = cal.getTime();

        cal.setTime(new Date());
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.MILLISECOND);
        Date beginningOfMonth = cal.getTime();

        List<Ordery> orders = em.createQuery("SELECT o FROM Ordery o WHERE o.date BETWEEN :beginningOfMonth AND :today AND o.customer.id = :id")
                .setParameter("beginningOfMonth", beginningOfMonth)
                .setParameter("today", today)
                .setParameter("id", customer.getId())
                .getResultList();

        return orders;
    }

    public void save(Ordery o){
        em.merge(o);
    }
    
    public void update(Ordery o){
        em.merge(o);
    }


    public Ordery get(final Date date, final int customerId) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        final int day = cal.get(Calendar.DAY_OF_MONTH);
        final int month = cal.get(Calendar.MONTH) + 1;
        final int year = cal.get(Calendar.YEAR);
        List<Ordery> o = em.createQuery("SELECT o FROM Ordery o WHERE EXTRACT(DAY FROM o.date) = :day AND EXTRACT(MONTH FROM o.date) = :month AND EXTRACT(YEAR FROM o.date) = :year AND o.customer.id = :id ")
                .setParameter("day", day)
                .setParameter("month", month)
                .setParameter("year", year)
                .setParameter("id", customerId)
                .getResultList();

        if(o.size() > 1){
            throw new NonUniqueResultException();
        }

        return o.size() == 1 ? o.get(0) : null;
    }
}
