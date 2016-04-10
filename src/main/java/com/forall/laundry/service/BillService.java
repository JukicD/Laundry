package com.forall.laundry.service;

import com.forall.laundry.logger.AppLogger;
import com.forall.laundry.model.Bill;
import com.forall.laundry.model.Customer;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by jd on 10/15/15.
 */
@Stateless
public class BillService implements Serializable {

    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private AppLogger logger;

    public void save(final Bill bill){
        try{
            em.persist(bill);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<Bill> getBilsFrom(final Customer customer){
        return em.createQuery("SELECT b FROM Bill b WHERE b.customer.id = :id")
                .setParameter("id", customer.getId())
                .getResultList();
    }

    public Bill get(final Date date, final int id){
        
        Bill b = em.createQuery("SELECT b FROM Bill b WHERE b.customer.id = :id AND b.printed = :date ", Bill.class)
                .setParameter("id", id)
                .setParameter("date", date)
                .getSingleResult();

        return b;
    }
    
    public void update(Bill bill){
        try{
            em.merge(bill);
        }catch(Exception e){
           logger.info("Error updating Bill with id " + bill.getId() + "!");
        }
    }
}
