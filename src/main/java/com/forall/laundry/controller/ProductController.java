/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.controller;

import com.forall.laundry.model.Customer;
import com.forall.laundry.model.Product;
import com.forall.laundry.service.CustomerService;
import com.forall.laundry.service.ProductService;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 *
 * @author jd
 */
@Named
@RequestScoped
public class ProductController implements Serializable{

    @Inject
    private UserController userController;

    @Inject
    private Product product;
    
    @EJB
    private ProductService productService;

    @EJB
    private CustomerService customerService;

    
    public ProductController() {
    }

    public void addProduct(){
        Customer customer = userController.getCustomer();
        customer.addProduct(product);

        productService.save(product);
        customerService.update(customer);
        product.setName(null);
        product.setPrice(null);
        product.setBorrowed(false);
    }
    
    public void save(){
        productService.save(product);
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductService getProductService() {
        return productService;
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }
}
