/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.controller;

import com.forall.laundry.model.Category;
import com.forall.laundry.model.Customer;
import com.forall.laundry.model.Price;
import com.forall.laundry.model.Product;
import com.forall.laundry.service.CategoryService;
import com.forall.laundry.service.CustomerService;
import com.forall.laundry.service.PriceService;
import com.forall.laundry.service.ProductService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

    @EJB
    private PriceService priceService;

    private Map<Product, Boolean> map;
    private List<Product> allProducts;

    @PostConstruct
    public void init(){
        map = new HashMap<>();
        allProducts = productService.getProducts().stream().sorted().collect(Collectors.toList());
    }

    public void addProduct(){
        productService.save(product);

        customerService.getAllCustomers().forEach(c -> {
            final Price price = new Price();
            price.setPrice(new BigDecimal(0.00));
            priceService.save(price);
            product.getPriceMap().put(c, price);
        });

        if(categoryController.getSelectedCategories() != null){

            categoryController.getSelectedCategories().stream().forEach((Category c) -> {
                categoryService.update(c);
                product.addCategory(c);
            });

        }
        
        productService.update(product);
        product.setName(null);
        categoryController.setSelectedCategories(null);
        categoryController.init();

    }
    
    public void delete(final Product product){
        Product prod = productService.find(product.getProduct_id());
        
        Collection<Price> prices = prod.getPriceMap().values();
        
        List<Customer> customers = customerService.getAllCustomers();
        
        prices.forEach(price -> {
            priceService.delete(price);
        });
        
        customers.stream().forEach(customer -> {
            if(customer.getPropertyMap().containsKey(prod)){
                customer.getPropertyMap().remove(prod);
                customerService.update(customer);
            }
        });
        
        
        
        productService.delete(prod);
    }

    public BigDecimal getPrice(final Customer customer, final Product product){
        return product.getPriceMap().get(customer).getPrice();
    }

    public BigDecimal getProductPrice(final Customer customer, final Product product){
        return product.getPriceMap().get(customer).getPrice();
    }

    public List<Product> getAllProducts(){
        return allProducts;
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

    public Map<Product, Boolean> getMap() {
        return map;
    }

    public void setMap(Map<Product, Boolean> map) {
        this.map = map;
    }

    public void update(final Product product, final Category cat){

        final Product p = productService.find(product.getProduct_id());
        final Category c = categoryService.find(cat.getId());

        if(p.getCategories().contains(cat)){
            p.getCategories().remove(c);
        }else{
            p.getCategories().add(c);
        }

        productService.update(p);
        categoryService.update(c);
    }
}
