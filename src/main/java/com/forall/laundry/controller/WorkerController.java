/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.controller;

import com.forall.laundry.model.Worker;
import com.forall.laundry.service.WorkerService;
import org.primefaces.context.RequestContext;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.FacesComponent;
import javax.faces.context.FacesContext;
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
    
    @PostConstruct
    public void init(){
        workers = workerService.getWorkers();
    }

    public void save(){
        workerService.save(worker);
        worker.setFirstName(null);
        worker.setLastName(null);
        init();

        RequestContext.getCurrentInstance().update("first:workerForm");
    }

    public void delete(final Worker w){

    }

    public List<Worker> getWorkers() {
        return workers;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }
}



