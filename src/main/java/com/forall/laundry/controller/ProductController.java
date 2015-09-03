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

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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

    private Map<Product, Boolean> map;

    @PostConstruct
    public void init(){
        map = new HashMap<>();

    }

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
        categoryController.init();

    }

    public List<Product> getAllProducts(){
        return productService.getProducts();
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

    public Map<Product, Boolean> getMap() {
        return map;
    }

    public void setMap(Map<Product, Boolean> map) {
        this.map = map;
    }

    public void update(Product product, Category cat){
        Product p = productService.find(product.getProduct_id());
        Category c = categoryService.find(cat.getId());

        if(p.getCategories().contains(cat)){
            p.getCategories().remove(c);
            c.getProduct().remove(p);
        }else{
            p.getCategories().add(c);
            c.getProduct().add(p);
        }

        productService.update(p);
        categoryService.update(c);


    }
}
