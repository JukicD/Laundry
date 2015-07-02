/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.hibernate;

import com.forall.laundry.model.Customer;
import com.forall.laundry.model.Item;
import com.forall.laundry.model.Ordery;
import com.forall.laundry.model.Worker;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

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
            configuration.addAnnotatedClass(Item.class);
            configuration.addAnnotatedClass(Worker.class);
            
            final ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
                                                        .applySettings(configuration.getProperties())
                                                        .buildServiceRegistry();
            
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }
        return sessionFactory;
    }
}
