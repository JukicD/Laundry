/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.EntityDAO;

import com.forall.laundry.model.Product;

/**
 *
 * @author jd
 */
public interface ProductDAO extends DAO<Product>{
    
    Product findByName(String name);
    
}
