/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.EntityDAO;

import com.forall.laundry.model.Item;
import com.forall.laundry.model.Ordery;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author jd
 */
public class ItemDAOImpl extends AbstractDAO<Item> implements ItemDAO{

    public List<Item> getItemsFrom(Ordery order) {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        List<Item> result;
        
        try{
            result = session.createQuery("SELECT i FROM Item i WHERE i.ordery.order_id = :id").setParameter("id", order.getOrder_id()).list();
            
            session.flush();
            tx.commit();
        }catch (Exception e){
            tx.rollback();
            throw e;
        }
        return result;
    }
    
    
    
}
