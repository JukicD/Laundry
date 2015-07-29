/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.controller;

import com.forall.laundry.model.Worker;
import com.forall.laundry.service.WorkerService;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

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



