/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.service;

import com.forall.laundry.logger.AppLogger;
import com.forall.laundry.model.Customer;
import com.forall.laundry.model.Item;
import com.forall.laundry.model.Ordery;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
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
    
    @EJB
    CustomerService customerService;
    
    @Inject
    AppLogger logger;
    
    public List<Item> getItemsFrom(Ordery order) {
        
            return em.createNamedQuery("Item.fromOrder")
                    .setParameter("id", order.getOrder_id())
                    .getResultList();
    }
    
    public void save(Item item){
        try{
            em.persist(item);
            logger.info("ITEM SUCCESSFULLY CREATED! " + item.toString());
        }catch (Exception e){
            logger.error("ERROR CREATING ITEM ! " + item.toString());
        
    }
        
    }
    
    public void update(Item item){
        try{
            em.merge(item);
            logger.info("Successfully updated : " +item.toString());
        }catch (Exception e){
            logger.error("Update-Failure! " + item.toString());
        }
    }
    
    public Item find(long id){
        return em.find(Item.class, id);
    }
    
    public List<Map.Entry<String, BigDecimal>> getTotalSum(Customer customer){
        
        List<Item> items = customerService
                                                            .findOrdersById(customer.getId())
                                                            .stream().flatMap(o -> o.getItems().stream())
                                                            .collect(Collectors.toList());
        
        // elimate duplicate item-names by adding their sum -> total-sum for a certain product which is needed for statistics
        Map<String, BigDecimal> sum = items
                                                                .stream()
                                                                .collect(Collectors
                                                                        .toMap(
                                                                                item -> item.getItem_product().getName(), //key
                                                                                item -> item.getSum(),                                // value
                                                                                (i1, i2) -> i1.add(i2)));                                  // execute if equal
        
        
        Comparator<Entry<String, BigDecimal>> cmp = (entry1, entry2) -> entry2.getValue().intValue() - entry1.getValue().intValue();
        
        List<Map.Entry<String, BigDecimal>> ordered = new ArrayList<>();
        
        ordered.addAll(sum.entrySet());
        
        Collections.sort(ordered, cmp);
        return ordered;
        
    }
    
    public List<Item> getSpecificItems(final String name, final Customer customer){
        
        List<Item> items = em.createQuery("Select i FROM Item i, Ordery o, Product p, Customer c WHERE c.id = :id AND c.id = o.customer.id AND i.ordery.order_id = o.order_id AND i.item_product.product_id = p.product_id AND p.name LIKE :name ORDER BY o.date ASC")
                .setParameter("id", customer.getId())
                .setParameter("name", name)
                .getResultList();
        
        return items;
    }
    
    public List<Item> getAllItems(Customer customer){
        
        List<Item> items = em.createQuery("SELECT i FROM Item i, Ordery o WHERE o.customer.id = :id AND i.ordery.order_id = o.order_id ORDER BY o.date ASC")
                .setParameter("id", customer.getId())
                .getResultList();
        
        return items;
    }
}
