/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.controller;

import com.forall.laundry.model.Worker;
import com.forall.laundry.service.WorkerService;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author jd
 */
@Named
@RequestScoped
public class WorkerController implements Serializable{
    
    @EJB
    private WorkerService workerService;
    
    @Inject
    private Worker worker;
    
    private List<Worker> workers;
    
    public List<Worker> getWorkers(){
        return workerService.getWorkers();
    }
    public void save(){
        workerService.save(worker);
        worker.setFirstName(null);
        worker.setLastName(null);
    }

    public void delete (final Worker worker){
        workerService.delete(worker);
    }
    
    public void update(){
        workerService.update(worker);
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }
}



