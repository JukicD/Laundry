/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.EntityDAO;

/**
 *
 * @author jd
 */
public interface DAO<T> {
    
    void create(T t);
    
    T getById(int id);
    
    void update(T t);
    
    void delete(T t);
    
    void saveOrUpdate(T t);
}
