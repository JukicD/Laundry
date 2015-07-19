/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.service;

import com.forall.laundry.model.Item;
import com.forall.laundry.model.Ordery;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author jd
 */
@Stateless
public class ItemService {

    @PersistenceContext
    EntityManager em;
    
    public List<Item> getItemsFrom(Ordery order) {
        
            return em.createNamedQuery("Item.fromOrder")
                    .setParameter("id", order.getOrder_id())
                    .getResultList();
    }
    
    public void save(Item item){
        em.persist(item);
    }
}