package com.forall.laundry.service;

import com.forall.laundry.model.Category;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

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
    public List<Category> getCategories(){
        return em.createNamedQuery("Category.all").getResultList();
    }

    public void update(Category category){
        em.merge(category);
    }

    public void save(Category category){
        em.persist(category);
    }
}
