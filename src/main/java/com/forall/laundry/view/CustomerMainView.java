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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by jd on 03.09.15.
 */
@Named
@ViewScoped
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

    private boolean isSelected;


    @PostConstruct
    public void init(){

        map = new HashMap<>();
        specificMap = new HashMap<>();
        Customer customer = userController.getCustomer();

        specificCategories = categoryService.getCategories()
                .stream()
                .filter( c -> !c.isForAll())
                .sorted((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()))
                .collect(Collectors.toList());

        List<Product> products = productService.getProducts();
        List<Product> customerP = productService.getProductsFrom(userController.getCustomer());
        Map<Product, Property> prodPropMapping = customer.getPropertyMap();

        specificCategories.forEach(c -> {
            Map<Product, Boolean> valueMap = new HashMap<>();
            products.forEach(p -> {
                valueMap.put(p, prodPropMapping.containsKey(p) ? prodPropMapping.get(p).getCategories().contains(c) : false);
            });
            specificMap.put(c, valueMap);
        });

        products.forEach(p -> map.put(p, customerP.contains(p)));
    }

    public void update(Product product){

        Customer customer = customerService.findById(userController.getCustomer().getId());

        List<Product> products = customer.getProducts();

        if(products.contains(product)){
            products.remove(product);

        }else{

            products.add(product);
            productService.update(product);
        }
        customerService.update(customer);
    }

    public void addCategory(Product product, Category category){

       Customer customer = userController.getCustomer();
        Map<Product, Property> map = customer.getPropertyMap();


        if(map.containsKey(product) && !map.containsValue(category)){
            Property prop = map.get(product);
            prop.getCategories().add(category);

            propertyService.update(prop);
        }else{

            Property prop = new Property();
            prop.getCategories().add(category);
            map.put(product,prop);
            propertyService.save(prop);
        }

        customerService.update(customer);
    }

    public boolean contains(Product product, Category category){

        Customer customer = userController.getCustomer();
        Map<Product, Property> mapping = customer.getPropertyMap();

        List<Category> categories = null;

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
