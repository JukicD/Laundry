/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.service;

import com.forall.laundry.logger.AppLogger;
import com.forall.laundry.model.Worker;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author jd
 */
@Stateless
public class WorkerService implements Serializable{
    
    @PersistenceContext
    private EntityManager em;

    @Inject
    private AppLogger logger;
    
    public List<Worker> getWorkers(){
        return (List<Worker>) em.createNamedQuery("Worker.findAll").getResultList().stream().sorted().collect(Collectors.toList());
    }

    public void save(Worker worker){
        try{
            em.persist(worker);
            logger.info("Worker created ! " + worker);
        }catch (Exception e){
            logger.error("Worker was not saved ! " + worker + " Cause: " + e.getCause());
        }
    }
    
    public void update(Worker worker){
        try{
            em.merge(worker);
            logger.info("Worker updated! " + worker);
        }catch (Exception e){
            logger.error("Worker was not updated! " + worker + " Cause: " + e.getCause());
        }
    }
}
