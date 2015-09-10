package com.forall.laundry.service;

import com.forall.laundry.model.Customer;
import com.forall.laundry.model.Price;
import com.forall.laundry.model.Product;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;

/**
 * Created by jd on 9/10/15.
 */
@Stateless
public class PriceService implements Serializable{

    @PersistenceContext
    private EntityManager em;

    public void update(final Price price){
        em.merge(price);
    }
}
