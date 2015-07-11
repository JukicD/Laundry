/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.EntityDAO;

import com.forall.laundry.model.Product;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author jd
 */
public class ProductDAOImpl extends AbstractDAO<Product> implements ProductDAO{

    @Override
    public Product findByName(String name) {
        Session session = getSession();
        Product result;
        Transaction tx = session.beginTransaction();
        try {
            
            result = (Product) session.createQuery("SELECT p FROM Product p WHERE p.name = :name").setParameter("name", name).uniqueResult();
            
            session.flush();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
        
        return result;
    }
    
}
