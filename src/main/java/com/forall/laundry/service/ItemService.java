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

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

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
    
    public List<Item> getItemsFrom(Ordery... order) {

        List<Object> orders = Arrays.asList(order);
        List<Item> items = new ArrayList<>();
        orders.stream().map( o -> ((Ordery) o)).forEach( o -> items.addAll(o.getPositionMap().keySet()));
            return items;
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
                                                            .stream().flatMap(o -> o.getPositionMap().keySet().stream())
                                                            .collect(Collectors.toList());
        
        // elimate duplicate item-names by adding their sum -> total-sum for a certain product which is needed for statistics
                                 // execute if equal
        
        
        Comparator<Entry<String, BigDecimal>> cmp = (entry1, entry2) -> entry2.getValue().intValue() - entry1.getValue().intValue();
        
        List<Map.Entry<String, BigDecimal>> ordered = new ArrayList<>();

        Collections.sort(ordered, cmp);
        return ordered;
        
    }
    
    public List<Item> getSpecificItems(final String name, final Customer customer){
        
        List<Item> items = em.createQuery("Select i FROM Item i, Ordery o, Product p, Customer c WHERE c.id = :id AND c.id = o.customer.id AND i.ordery.id = o.id AND i.product.product_id = p.product_id AND p.name LIKE :name ORDER BY o.date ASC")
                .setParameter("id", customer.getId())
                .setParameter("name", name)
                .getResultList();
        
        return items;
    }
    
    /**
     * 
     * @param customer The customer of whom all Items will be returned.
     * 
     * @return Returns a list of items which are ordered ascending according to the date of the containing ordery.
     *                 
     */
    public List<Item> getAllItems(Customer customer){
        
        List<Item> items = em.createQuery("SELECT i FROM Item i, Ordery o WHERE o.customer.id = :id AND i.ordery.id = o.id ORDER BY o.date ASC")
                .setParameter("id", customer.getId())
                .getResultList();
        
        return items;
    }
    
    /**
     *  Sums up items which have the same name and same dd-MM-yyyy date.
     * 
     * @param items List of items with same name.
     * 
     * @return sums up items with same name and same da
     */
    public Map<Date, BigDecimal> getSumOfItems(List<Item> items){
        
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yy");
        Map<Date, List<Item>> orderByDate = items.stream().collect(Collectors.groupingBy((Item i) -> i.getOrdery().trimedDate()));
        
        Map<Date, BigDecimal> orderedSums = new TreeMap<>((Object o1, Object o2) -> ((Date)o1).compareTo(((Date)o2)));


        /**ToDo**/
       return orderedSums;
    }
    
    /**
     * 
     * @param customer Customer
     * @return Returns a map in which all Items are grouped according to their name.
     */
    public Map<String, List<Item>> getItemsGroupedByName(Customer customer){
        
         return getAllItems(customer)
                 .stream()
                 .collect(Collectors.groupingBy(Item::getName));
         }

    public Item findItemWithProductID(long id){
        return (Item) em.createQuery("SELECT i FROM Item i WHERE i.product.id = :id")
                .setParameter("id", id)
                .getResultList().get(0);
    }
}
