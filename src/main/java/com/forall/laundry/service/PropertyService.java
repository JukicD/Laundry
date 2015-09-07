package com.forall.laundry.service;

import com.forall.laundry.logger.AppLogger;
import com.forall.laundry.model.Customer;
import com.forall.laundry.model.Product;
import com.forall.laundry.model.Property;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.Map;

/**
 * Created by jd on 06.09.15.
 */
@Stateless
public class PropertyService implements Serializable{

    @PersistenceContext
    private EntityManager em;

    @Inject
    private AppLogger logger;

    public void save(Property property){
        em.persist(property);
    }

    public void update(Property property){
        try{
            em.merge(property);
            logger.info("Property successfully updated! " + property);
        }catch (Exception e){
            logger.info("Property was not updated! Cause: " + e.getCause());
        }
    }

}
