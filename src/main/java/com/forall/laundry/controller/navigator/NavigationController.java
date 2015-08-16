/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.controller.navigator;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.io.Serializable;

/**
 *
 * @author jd
 */
@Named
@ApplicationScoped
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