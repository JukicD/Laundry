/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.controller.navigator;

import com.forall.laundry.logger.AppLogger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Stateful;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 *
 * @author jd
 */
@Named
@SessionScoped
public class NavigationController implements Serializable{

    private String site;

    @PostConstruct
    public void init(){
        site ="customerList";
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;

    }
}