/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.service;

import com.forall.laundry.EntityDAO.OrderyDAOImpl;
import com.forall.laundry.model.Customer;
import com.forall.laundry.model.Ordery;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author jd
 */
public class OrderyService {
    
    @Inject
    private OrderyDAOImpl dao;
    
    public void saveOrUpdate(Ordery ordery){
        dao.saveOrUpdate(ordery);
    }

    public OrderyDAOImpl getDao() {
        return dao;
    }

    public void setDao(OrderyDAOImpl dao) {
        this.dao = dao;
    }

    public List<Ordery> getOrdersFrom(Customer customer) {
       return dao.getOrdersFrom(customer);
    }

    public List<Ordery> getOldOrdersFrom(Customer customer) {
        return dao.getOldOrdersFrom(customer);
    }
}
