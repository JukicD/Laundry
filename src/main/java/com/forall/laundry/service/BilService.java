package com.forall.laundry.service;

import com.forall.laundry.model.Bil;
import com.forall.laundry.model.Customer;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.Date;
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

    public Bil get(final Date date, final int id){
        
        Bil b = em.createQuery("SELECT b FROM Bil b WHERE b.customer.id = :id AND b.printed = :date ", Bil.class)
                .setParameter("id", id)
                .setParameter("date", date)
                .getSingleResult();

        return b;

    }
    
    public void update(Bil bil){
        try{
            em.merge(bil);
        }catch(Exception e){
            System.out.println("Error updating Bill with id " + bil.getId() + "!");
        }
    }
}
