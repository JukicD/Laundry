/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.forall.laundry.service;

import com.forall.laundry.model.Customer;
import com.forall.laundry.model.Item;
import com.forall.laundry.model.Ordery;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author jd
 */
@Stateless
public class StatisticService implements Serializable{
    
    @PersistenceContext
    private EntityManager em;
    
    @EJB
    private CustomerService customerService;
    
    public BigDecimal getSumFromMonth(int month, int year){
        
        List<Ordery> orderys = em.createQuery("SELECT o From Ordery o WHERE EXTRACT(MONTH FROM time) = :month AND EXTRACT(YEAR FROM time) = :year")
                .setParameter("month", month + 1)
                .setParameter("year", year)
                .getResultList();

        /**ToDo; **/
        return new BigDecimal(1);
    }
    
    public BigDecimal getSumFromCustomer(Customer customer){
        
        List<Ordery> orders = customerService.findOrdersById(customer.getId());
        List<Item> items = orders
                .stream()
                .flatMap((Ordery o) -> o.getItems().stream())
                .collect(Collectors.toList());
        /**ToDo:**/
        return new BigDecimal(1);
    }
}
