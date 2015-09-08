/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.service;

import com.forall.laundry.logger.AppLogger;
import com.forall.laundry.model.Customer;
import com.forall.laundry.model.Product;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author jd
 */
@Stateless
public class ProductService {

    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private AppLogger logger;
    
    public void save(Product product){
        em.persist(product);
    }

    public void update(Product editedProduct) {
        try{
             em.merge(editedProduct);
             logger.info("Product was successfully updated! " + editedProduct.toString());
        }catch (Exception e){
            logger.error("Failure updating Product!" + editedProduct.toString());
        }
       
    }

    public Product find(long id){
        return em.find(Product.class, id);
    }

    public List<Product> getProductsFrom(Customer customer) {

        return customer.getPropertyMap().keySet().stream().collect(Collectors.toList());

    }

    public List<Product> getProducts(){
        return em.createNamedQuery("Product.findAll").getResultList();
    }

    public void delete(Product product) {
        em.remove(product);
    }
}



