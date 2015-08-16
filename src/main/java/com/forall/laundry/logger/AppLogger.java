/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.logger;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Startup;
import javax.inject.Singleton;
import java.io.Serializable;

/**
 *
 * @author jd
 */
@Startup
@Singleton
public class AppLogger implements Serializable{
    
    private final Logger logger = LoggerFactory.getLogger(AppLogger.class);
    
    public Logger getLogger(){
        return logger;
    }
    
    public void info(String info){
        logger.info(info);
    }
    
    public void warn(String warn){
        logger.warn(warn);
    }
    
    public void error(String error){
        logger.error(error);
    }
}