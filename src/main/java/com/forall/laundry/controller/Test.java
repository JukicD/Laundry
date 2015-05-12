package com.forall.laundry.controller;

import com.forall.laundry.model.Customer;
import com.forall.laundry.model.Item;
import com.forall.laundry.model.Purchase;
import com.forall.laundry.model.Worker;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by jd on 5/11/15.
 */

public class Test {

    public static void main(String[] args){

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("laundryPU");
        EntityManager em = emf.createEntityManager();

        Customer customer = new Customer();
        customer.setName("Dejan");
        Customer cust = new Customer();
        cust.setName("Andi");

        Item item = new Item();

        item.setAmount(3);
        item.setType("rented");
        customer.addItem(item);

        Worker worker = new Worker();
        Worker w2 = new Worker();
        w2.setName("Susi");
        worker.setName("Petra");
        worker.setSurname("Heimbuchner");

        Purchase purchase = new Purchase();
        Purchase pur = new Purchase();
        pur.addWorker(worker);
        pur.addWorker(w2);
        purchase.addWorker(worker);
        purchase.addWorker(w2);
        purchase.setCustomer(customer);

        em.getTransaction().begin();
        em.persist(customer);
        em.persist(cust);
        em.persist(item);
        em.persist(purchase);
        em.persist(pur);
        em.getTransaction().commit();
        em.close();
        System.out.println("done");
    }
}
