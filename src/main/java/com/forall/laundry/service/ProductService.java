/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.service;

import com.forall.laundry.EntityDAO.ProductDAOImpl;
import com.forall.laundry.model.Product;
import javax.inject.Inject;

/**
 *
 * @author jd
 */
public class ProductService {
    
    @Inject
    private ProductDAOImpl productDAO;

    public ProductService() {
    }

    public void save(Product product){
        productDAO.saveOrUpdate(product);
    }
    
    public ProductDAOImpl getProductDAO() {
        return productDAO;
    }

    public void setProductDAO(ProductDAOImpl productDAO) {
        this.productDAO = productDAO;
    }
    
}
