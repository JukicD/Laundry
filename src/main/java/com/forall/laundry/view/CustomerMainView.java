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

        final Customer customer = customerService.findById(userController.getCustomer().getId());

        specificCategories = categoryService.getCategories()
                .stream()
                .filter( c -> !c.isForAll())
                .sorted((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()))
                .collect(Collectors.toList());

        final List<Product> products = productService.getProducts();
        final Set<Product> customerP = customer.getProducts();
        final Map<Product, Property> prodPropMapping = customer.getPropertyMap();

        specificCategories.forEach(c -> {
            final Map<Product, Boolean> valueMap = new HashMap<>();
            products.forEach(p -> {
                valueMap.put(p, prodPropMapping.containsKey(p) ? prodPropMapping.get(p).getCategories().contains(c) : false);
            });
            specificMap.put(c, valueMap);
        });

        products.forEach(p -> {
            map.put(p, customerP.contains(p));
        });
    }

    public void update(final Product product){

        final Customer customer = customerService.findById(userController.getCustomer().getId());
        final Set<Product> products = customer.getProducts();
        final Map<Product, Property> map = customer.getPropertyMap();

        if(products.contains(product)){
            products.remove(product);
            map.remove(product);
        }else{

            final Property prop = new Property();
            map.put(product, prop);
            products.add(product);
            propertyService.save(prop);
        }
        customerService.update(customer);
    }

    public void addCategory(final Product product, final Category category){

        final Customer customer = customerService.findById(userController.getCustomer().getId());
        final Map<Product, Property> map = customer.getPropertyMap();

        if(map.containsKey(product)){

            final Property prop = map.get(product);
            final Set<Category> categories = prop.getCategories();
            if(categories.contains(category)){

                categories.remove(category);
                propertyService.update(prop);
            }else{
                categories.add(category);
                propertyService.update(prop);
            }
        }
    }

    public boolean contains(final Product product, final Category category){

        final Customer customer = customerService.findById(userController.getCustomer().getId());
        final Map<Product, Property> mapping = customer.getPropertyMap();
        Set<Category> categories = null;

        if(mapping.containsKey(product)){
            categories = mapping.get(product).getCategories();
        }

        return categories != null && categories.contains(category);
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
