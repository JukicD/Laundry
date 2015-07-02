/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.service;

import java.util.Set;

/**
 *
 * @author jd
 */
public interface EntityService<T, E> {
    
    public void save();
    
    public Set<T> getAll();
    
    public <E> E getAllE();
    
}
