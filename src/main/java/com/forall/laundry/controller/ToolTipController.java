/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import org.primefaces.context.RequestContext;

/**
 *
 * @author jd
 */
@Named
@ApplicationScoped
public class ToolTipController {
    
    private boolean enabled;
    
    @PostConstruct
    public void init(){
        enabled = true;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    public void change(){
        enabled = !enabled;
        RequestContext.getCurrentInstance().update("toolTipFade");
    }
}
