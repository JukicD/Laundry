package com.forall.laundry.service;

import com.forall.laundry.model.Bil;
import com.forall.laundry.model.Customer;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.Calendar;
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
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        final int day = cal.get(Calendar.DAY_OF_MONTH);
        final int month = cal.get(Calendar.MONTH) + 1;
        final int year = cal.get(Calendar.YEAR);

        List<Bil> b = em.createQuery("SELECT b FROM Bil b WHERE EXTRACT(DAY FROM b.printed) = :day AND EXTRACT(MONTH FROM b.printed) = :month AND EXTRACT(YEAR FROM b.printed) = :year AND b.customer.id = :id ")
                .setParameter("day", day)
                .setParameter("month", month)
                .setParameter("year", year)
                .setParameter("id", id)
                .getResultList();

        return b.get(0);

    }
}
