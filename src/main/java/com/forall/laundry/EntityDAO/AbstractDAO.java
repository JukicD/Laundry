/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.EntityDAO;

import com.forall.laundry.hibernate.HibernateUtil;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author jd
 */
public class AbstractDAO<T> implements DAO<T> {

    private final Class<T> type;

    public AbstractDAO() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        type = (Class) pt.getActualTypeArguments()[0];
    }
    
    @Override
    public void create(T t) {
        Session session = getSession();
        Transaction tx = getSession().beginTransaction();
        
        try{
            session.save(t);
            session.flush();
            tx.commit();
        }catch (Exception e){
            tx.rollback();
            throw e;
        }finally{
            session.close();
        }
        
    }

    @Override
    public T getById(int id) {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        T t;
        try{
            t = (T) session.get(type, id);
        }catch (Exception e){
            tx.rollback();
            throw e;
        }finally{
            session.close();
        }
        
        return t;
    }

    @Override
    public void update(T t) {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        
        try {
            session.update(t);
            session.flush();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }finally{
            session.close();
        }
    }

    @Override
    public void delete(T t) {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        
        try {
            session.delete(t);
            session.flush();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }finally{
            session.close();
        }
    }
    
    

    @Override
    public void saveOrUpdate(T t) {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        
        try {
            session.saveOrUpdate(t);
            session.flush();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }finally{
            session.close();
        }
    }
    
    protected Session getSession(){
        return HibernateUtil.getSessionFactory().openSession();
    }
}
