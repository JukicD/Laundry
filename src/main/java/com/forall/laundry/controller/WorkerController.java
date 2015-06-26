/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.controller;

import com.forall.laundry.model.Worker;
import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;

@Named
@RequestScoped
public class WorkerController implements Serializable{
    
    @PersistenceUnit
    private EntityManagerFactory emf;
    
    @Resource
    private UserTransaction ut;
    
    @Inject
    private Worker worker;
    
    public Worker getWorker(){
        return this.worker;
    }
    
    public void setWorker(Worker worker){
        this.worker = worker;
    }
   
    public String persist(){
           try {
                ut.begin();
                emf.createEntityManager().persist(worker);
                ut.commit();
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            
        }
        worker.setName(null);
        worker.setSurname(null); 
        return "/pages/page1.xhtml";
    }
    
    @Transactional
    public String remove(Worker w) {
      
        emf.createEntityManager()
                .createQuery("DELETE FROM Worker w Where w.id = :id")
                .setParameter("id", w.getId())
                .executeUpdate();
        
        return null;
    }
    
    public List<Worker> getWorkers(){
       
        return emf.createEntityManager().createQuery("SELECT w From Worker w").getResultList();
    }
    
    public String redirectToAddWorker(){
        return "/pages/page1.xhtml";
    }
    
    public String redirectToMaintainWorker(){
        return "/pages/mitarbeiter.xhtml";
    }
    
    public String back(){
        return"/pages/workerMain.xhtml";
    }
}