package com.forall.laundry.view;

import com.forall.laundry.controller.UserController;
import com.forall.laundry.model.Category;
import com.forall.laundry.model.Customer;
import com.forall.laundry.model.Product;
import com.forall.laundry.model.Property;
import com.forall.laundry.service.CategoryService;
import com.forall.laundry.service.CustomerService;
import com.forall.laundry.service.ProductService;
import com.forall.laundry.service.PropertyService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;
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

    @EJB
    private PropertyService propertyService;

    @Inject
    private UserController userController;

    private Map<Product, Boolean> map;

    private List<Category> specificCategories;

    private Map<Category, Map<Product, Boolean>> specificMap;

    @PostConstruct
    public void init(){

        map = new HashMap<>();
        specificMap = new HashMap<>();
        Customer customer = customerService.findById(userController.getCustomer().getId());

        specificCategories = categoryService.getCategories()
                .stream()
                .filter( c -> !c.isForAll())
                .sorted((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()))
                .collect(Collectors.toList());

        List<Product> products = productService.getProducts();
        Set<Product> customerP = customer.getProducts();
        Map<Product, Property> prodPropMapping = customer.getPropertyMap();

        specificCategories.forEach(c -> {
            Map<Product, Boolean> valueMap = new HashMap<>();
            products.forEach(p -> {
                valueMap.put(p, prodPropMapping.containsKey(p) ? prodPropMapping.get(p).getCategories().contains(c) : false);
            });
            specificMap.put(c, valueMap);
        });

        products.forEach(p -> {
            map.put(p, customerP.contains(p));
            System.out.println(customerP.contains(p));
        });
    }

    public void update(Product product){

        Customer customer = customerService.findById(userController.getCustomer().getId());

        Set<Product> products = customer.getProducts();

        Map<Product, Property> map = customer.getPropertyMap();

        if(products.contains(product)){
            products.remove(product);
            map.remove(product);
        }else{

            Property prop = new Property();
            map.put(product, prop);
            products.add(product);
            propertyService.save(prop);
        }
        customerService.update(customer);
        init();
    }

    public void addCategory(Product product, Category category){

       Customer customer = customerService.findById(userController.getCustomer().getId());

        Map<Product, Property> map = customer.getPropertyMap();

        System.out.println("Product: " + product.getName() + " Category: " + category.getName() + " Bool: " + map.containsKey(product));
        if(map.containsKey(product)){

            Property prop = map.get(product);
            System.out.println("Property: " + prop.getId());
            Set<Category> categories = prop.getCategories();
            System.out.println("Categories: " + categories.size() + " Bool: " + categories.contains(category));
            if(categories.contains(category)){
                categories.remove(category);
                propertyService.update(prop);

            }else{
                System.out.println("ADD");
                categories.add(category);
                propertyService.update(prop);
            }
        }
        System.out.println("addCategory");
        init();
    }

    public boolean contains(Product product, Category category){

        Customer customer = userController.getCustomer();
        Map<Product, Property> mapping = customer.getPropertyMap();

        Set<Category> categories = null;

        if(mapping.containsKey(product)){
            categories = mapping.get(product).getCategories();
        }

        return categories == null ? false : categories.contains(category);
    }

    public boolean isSelected(Product product){

      List<Product> products = productService.getProductsFrom(userController.getCustomer());
        return !products.contains(product);
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

    public Map<Product, Boolean> getMap() {
        return map;
    }

    public void setMap(Map<Product, Boolean> map) {
        this.map = map;
    }
}
