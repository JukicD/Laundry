/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.controller;

import com.forall.laundry.model.Category;
import com.forall.laundry.model.Customer;
import com.forall.laundry.model.Product;
import com.forall.laundry.service.CategoryService;
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

    @Inject
    private CategoryController categoryController;
    
    @EJB
    private ProductService productService;

    @EJB
    private CustomerService customerService;

    @EJB
    private CategoryService categoryService;

    public void addProduct(){
        productService.save(product);

        categoryController.getSelectedCategories().stream().peek( c -> System.out.println(c.getName())).forEach((Category c) -> {
            c.addProduct(product);
            categoryService.update(c);
            product.addCategory(c);
            productService.update(product);

        });
        product.setName(null);
        product.setPrice(null);
        product.setBorrowed(false);
        categoryController.setSelectedCategories(null);

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
