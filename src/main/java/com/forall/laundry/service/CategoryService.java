package com.forall.laundry.service;

import com.forall.laundry.model.Category;
import com.forall.laundry.model.Customer;
import com.forall.laundry.model.Product;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by jd on 9/2/15.
 */
@Stateless
public class CategoryService implements Serializable{

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
        em.persist(category);
    }

    public List<Category> getCategoriesFrom(Customer customer) {
        return em.createQuery("SELECT c FROM Category c, Customer cu WHERE cu.id = :id")
                .setParameter("id", customer.getId())
                .getResultList();
    }
}
