package com.forall.laundry.service;

import com.forall.laundry.model.Bil;
import com.forall.laundry.model.Customer;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

/**
 * Created by jd on 10/15/15.
 */
@Stateless
public class BilService implements Serializable {

    @PersistenceContext
    private EntityManager em;

    public void save(final Bil bil){
        em.persist(bil);
    }

    public List<Bil> getBilsFrom(final Customer customer){
        return em.createQuery("SELECT b FROM Bil b WHERE b.customer.id = :id")
                .setParameter("id", customer.getId())
                .getResultList();
    }

    public Bil get(final int day, final int month, final int year, final int id){
        System.out.println(day + month + year);
        
       List<Bil> list;
        
        if(month < 0){
            list = em.createQuery("Select b From Bil b WHERE EXTRACT(year from printed) = :year AND b.customer.id = :id")
                .setParameter("year", year)
                .setParameter("id", id)
                .getResultList(); 
        }
        else if(day < 0){
            list = em.createQuery("Select b From Bil b WHERE EXTRACT(month from printed) = :month AND EXTRACT(year from printed) = :year AND b.customer.id = :id")
                .setParameter("month", month)
                .setParameter("year", year)
                .setParameter("id", id)
                .getResultList();
        }
        else{
            list = em.createQuery("Select b From Bil b WHERE EXTRACT(DAY from printed) = :day AND EXTRACT(month from printed) = :month AND EXTRACT(year from printed) = :year AND b.customer.id = :id")
                .setParameter("day", day)
                .setParameter("month", month)
                .setParameter("year", year)
                .setParameter("id", id)
                .getResultList();
        }
        
        return list.get(0);
    }
}
