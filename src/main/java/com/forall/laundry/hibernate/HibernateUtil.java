/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.hibernate;

import com.forall.laundry.model.Customer;
import com.forall.laundry.model.Ordery;
import com.forall.laundry.model.Product;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author jd
 */
public class HibernateUtil {

    private static SessionFactory sessionFactory;
    
    public static SessionFactory getSessionFactory() {
        if(sessionFactory == null){
            Configuration configuration = new Configuration();
            configuration.configure();
            
            configuration.addAnnotatedClass(Customer.class);
            configuration.addAnnotatedClass(Ordery.class);
            configuration.addAnnotatedClass(Product.class);
            
            final ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                                                        .applySettings(configuration.getProperties())
                                                        .build();
            
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }
        return sessionFactory;
    }
}
