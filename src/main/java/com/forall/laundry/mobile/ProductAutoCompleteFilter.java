package com.forall.laundry.mobile;

import com.forall.laundry.controller.selection.CustomerSelectionController;
import com.forall.laundry.model.*;
import com.forall.laundry.service.CustomerService;
import com.forall.laundry.service.ItemService;
import com.forall.laundry.service.ProductService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by jd on 8/21/15.
 */
@Named
@SessionScoped
public class ProductAutoCompleteFilter implements Serializable{

    @EJB
    private ProductService productService;

    @EJB
    private CustomerService customerService;

    @Inject
    private MobileController mobileController;

    private List<Product> products;
    private String query;

    @Inject
    private MobileController mc;

    @PostConstruct
    public void init(){
        products = filterItems();
    }

    public List<Product> filterItems(){
        if(mc.getCustomer() == null){
            return null;
        }
        if(query == null || query.equals("")){
            products = new ArrayList<>();

            Customer customer = customerService.findById(mobileController.getCustomer().getId());
            Map<Product, Property> map = customer.getPropertyMap();
            Category chosenCat = mobileController.getCategory();

            map.entrySet().stream().forEach((Map.Entry<Product, Property> e) -> {
                if (e.getKey().getCategories().contains(chosenCat) || e.getValue().getCategories().contains(chosenCat)){
                    products.add(e.getKey());
                }

            });
           return products;
        }

        List<Product> filter = new ArrayList<>();

        products
                .parallelStream()
                .filter(c -> c.getName().toLowerCase().contains(query.toLowerCase()))
                .forEach(filter::add);
        filter.sort((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()));

        return filter;
    }

    public void reset(){
        query = null;
        init();
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
