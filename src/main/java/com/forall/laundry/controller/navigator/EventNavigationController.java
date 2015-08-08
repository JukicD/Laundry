/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.controller.navigator;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author jd
 */
@Named
@ViewScoped
public class EventNavigationController implements Serializable{
    
    private String site;

    @PostConstruct
    public void init(){
        site ="event";
    }
    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }
}
