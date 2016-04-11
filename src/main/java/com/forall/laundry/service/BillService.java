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
import java.util.stream.Collectors;
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

    public List<Bill> get(final Date date, final int id){
        
        Customer customer = em.createQuery("SELECT c FROM Customer c WHERE c.id = :id", Customer.class)
                .setParameter("id", id)
                .getSingleResult();

        return customer.getBills().stream().filter(bill -> bill.getPrinted().equals(date)).collect(Collectors.toList());
    }
    
    public void update(Bill bill){
        try{
            em.merge(bill);
        }catch(Exception e){
           logger.info("Error updating Bill with id " + bill.getId() + "!");
        }
    }

    public List<Bill> getBillsFrom(Customer customer) {
       Customer cus = em.createQuery("SELECT c FROM Customer c WHERE c.id = :id", Customer.class)
                .setParameter("id", customer.getId())
                .getSingleResult();
        return cus.getBills();
    }
}
