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

        System.out.println("CUSTOMERMAINVIEW INIT");
        map = new HashMap<>();
        specificMap = new HashMap<>();
        Customer customer = userController.getCustomer();

        specificCategories = categoryService.getCategories()
                .stream().peek( c -> System.out.println(c.getName()))
                .filter( c -> !c.isForAll())
                .sorted((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()))
                .collect(Collectors.toList());

        System.out.println("SPEC: " + specificCategories.size());

        List<Product> products = productService.getProducts();
        List<Product> customerP = productService.getProductsFrom(userController.getCustomer());
        Map<Product, Property> prodPropMapping = customer.getPropertyMap();

        System.out.println("INT: " + prodPropMapping.entrySet().size());
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
            System.out.println(customer.getName() + " remove " + product.getName());
        }else{
            products.add(product);
            System.out.println(customer.getName() + " add" + product.getName());
        }
        customerService.update(customer);
    }

    public void addCategory(Product product, Category category){

       Customer customer = userController.getCustomer();
        Map<Product, Property> map = customer.getPropertyMap();


        if(map.containsKey(product)){
            Property prop = map.get(product);
            prop.getCategories().add(category);

            propertyService.update(prop);
        }else{
            Property newProp = new Property();
            newProp.getCategories().add(category);
            map.put(product, newProp);

            propertyService.save(newProp);
           customerService.update(customer);
        }
       System.out.println(map.entrySet().size());
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
