package com.forall.laundry.view;

import com.forall.laundry.controller.UserController;
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
import java.util.stream.Collectors;

/**
 * Created by jd on 03.09.15.
 */
@Named
@RequestScoped
public class CustomerMainView implements Serializable{

    @EJB
    private CategoryService categoryService;

    @EJB
    private ProductService productService;

    @EJB
    private CustomerService customerService;

    @Inject
    private UserController userController;

    private Map<Product, Boolean> map;

    private List<Category> specificCategories;

    private Map<Category, Map<Product, Boolean>> specificMap;

    private List<Product> selectedProducts;

    @PostConstruct
    public void init(){
        map = new HashMap<>();
        specificMap = new HashMap<>();

        specificCategories = categoryService.getCategories()
                .stream()
                .filter(c -> !c.isForAll())
                .sorted((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()))
                .collect(Collectors.toList());

        List<Product> products = productService.getProducts();

        specificCategories.stream().forEach(c -> {
            Map<Product, Boolean> valueMap = new HashMap<>();
            products.stream().forEach(p -> {
                valueMap.put(p,p.getCategories().contains(c));

            });
            specificMap.put(c,valueMap);
        });

        products.forEach( p -> {
            map.put(p, userController.getCustomer().getProducts().contains(p));
        });
    }

    public void addProducts(){
        Customer customer = customerService.findById(userController.getCustomer().getId());

        selectedProducts.stream().forEach( p -> {
            if(!customer.getProducts().contains(p)){
                customer.getProducts().add(p);
            }
        });
        selectedProducts.stream().forEach( p -> System.out.println(p.getName()));
        customerService.update(customer);
    }

    public List<Category> getSpecificCategories() {
        return specificCategories;
    }

    public void setSpecificCategories(List<Category> specificCategories) {
        this.specificCategories = specificCategories;
    }

    public Map<Category, Map<Product, Boolean>> getSpecificMap() {
        return specificMap;
    }

    public void setSpecificMap(Map<Category, Map<Product, Boolean>> specificMap) {
        this.specificMap = specificMap;
    }

    public List<Product> getSelectedProducts() {
        return selectedProducts;
    }

    public void setSelectedProducts(List<Product> selectedProducts) {
        this.selectedProducts = selectedProducts;
    }

    public Map<Product, Boolean> getMap() {
        return map;
    }

    public void setMap(Map<Product, Boolean> map) {
        this.map = map;
    }
}
