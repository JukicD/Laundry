package com.forall.laundry.service;

import com.forall.laundry.controller.GrowlController;
import com.forall.laundry.logger.AppLogger;
import com.forall.laundry.model.Category;
import com.forall.laundry.model.Customer;
import com.forall.laundry.model.Product;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.inject.Inject;

/**
 * Created by jd on 9/2/15.
 */
@Stateless
public class CategoryService implements Serializable{

    @EJB
    private CustomerService customerService;
    
    @Inject
    private GrowlController gc;
    
    @Inject
    private AppLogger logger;

    @PersistenceContext
    private EntityManager em;

    public Category findBy(String s){
         Category cat = (Category) em.createQuery("SELECT c FROM Category c WHERE c.name = :name")
                .setParameter("name", s)
                 .getSingleResult();

        return cat;

    }

    public Category find(long id){
        return em.find(Category.class, id);
    }
    public List<Category> getCategories(){
        return em.createNamedQuery("Category.all").getResultList();
    }

    public void update(Category category){
        em.merge(category);
    }

    public void save(Category category){
            em.merge(category);
    }

    public List<Category> getCategoriesFrom(final Customer customer) {

        if(customer != null){
            final Customer c = customerService.findById(customer.getId());

            List<Category> categories = c.getPropertyMap().values().stream().flatMap(p -> p.getCategories().stream()).distinct().collect(Collectors.toList());

            List<Category> cat = c.getPropertyMap().keySet().stream().flatMap( p -> p.getCategories().stream()).distinct().collect(Collectors.toList());

            cat.addAll(categories);
            return cat.stream().distinct().collect(Collectors.toList());
        }
       return null;
    }

    public void delete(long id) {
        
        em.createNamedQuery("Category.remove").setParameter("id", id).executeUpdate();
    }
}
