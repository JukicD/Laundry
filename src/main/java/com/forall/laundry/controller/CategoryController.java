package com.forall.laundry.controller;

import com.forall.laundry.model.Category;
import com.forall.laundry.model.Product;
import com.forall.laundry.service.CategoryService;
import com.forall.laundry.service.ProductService;
import org.primefaces.component.selectbooleancheckbox.SelectBooleanCheckbox;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by jd on 9/2/15.
 */
@Named
@RequestScoped
public class CategoryController implements Serializable{

    @EJB
    private CategoryService categoryService;

    @EJB
    private ProductService productService;

    @Inject
    private Category category;

    private List<Category> categories;

    private List<Category> specificCategories;

    private List<Category> selectedCategories;

    private Map<Category, Map<Product, Boolean>> map;

    private Map<Category, Map<Product, Boolean>> specificMap;

    @PostConstruct
    public void init() {
        map = new HashMap<>();
        specificMap = new HashMap<>();


        categories = categoryService.getCategories()
                .stream()
                .filter(c -> c.isForAll())
                .sorted((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()))
                .collect(Collectors.toList());

        specificCategories = categoryService.getCategories()
                .stream()
                .filter(c -> !c.isForAll())
                .sorted((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()))
                .collect(Collectors.toList());

        List<Product> products = productService.getProducts();

        categories.stream().forEach(c -> {
            Map<Product, Boolean> valueMap = new HashMap<>();
            products.stream().forEach(p -> {
                valueMap.put(p,p.getCategories().contains(c));

            });
            map.put(c,valueMap);
        });

        specificCategories.stream().forEach(c -> {
            Map<Product, Boolean> valueMap = new HashMap<>();
            products.stream().forEach(p -> {
                valueMap.put(p,p.getCategories().contains(c));

            });
            specificMap.put(c,valueMap);
        });
    }

    public void update(Category category){
        categoryService.update(category);
    }

    public void save(){
        categoryService.save(category);
        category.setName(null);
        category.setForAll(false);
        init();
    }

    public List<Category> getSelectedCategories() {
        return selectedCategories;
    }

    public void setSelectedCategories(List<Category> selectedCategories) {
        this.selectedCategories = selectedCategories;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Map<Category, Map<Product, Boolean>> getMap() {
        return map;
    }

    public void setMap(Map<Category, Map<Product, Boolean>> map) {
        this.map = map;
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
}
