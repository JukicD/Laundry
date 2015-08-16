/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.service;

import com.forall.laundry.model.Worker;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author jd
 */
@Stateless
public class WorkerService implements Serializable{
    
    @PersistenceContext
    private EntityManager em;
    
    public List<Worker> getWorkers(){
        return em.createNamedQuery("Worker.findAll").getResultList();
    }
    public void save(Worker worker){
        em.persist(worker);
    }
    
    public void update(Worker worker){
        em.merge(worker);
    }
}
