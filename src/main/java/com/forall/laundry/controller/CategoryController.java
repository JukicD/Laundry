package com.forall.laundry.controller;

import com.forall.laundry.model.Category;
import com.forall.laundry.model.Product;
import com.forall.laundry.service.CategoryService;
import com.forall.laundry.service.ProductService;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import org.primefaces.event.SelectEvent;

/**
 * Created by jd on 9/2/15.
 */
@Named
@ViewScoped
public class CategoryController implements Serializable{

    @EJB
    private CategoryService categoryService;

    @EJB
    private ProductService productService;

    @Inject
    private Category category;
    
    @Inject
    private GrowlController gc;

    private List<Category> categories;
    private List<Category> specificCategories;
    private List<Category> selectedCategories;
    private Map<Category, Map<Product, Boolean>> map;
    private Map<Category, Map<Product, Boolean>> specificMap;
    private Category selectedCategory;

    @PostConstruct
    public void init() {
        map = new HashMap<>();
        specificMap = new HashMap<>();

        categories = categoryService.getCategories()
                .stream()
                .filter(Category::isForAll)
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
                valueMap.put(p, p.getCategories().contains(c));
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
        init();
    }

    public void save(){
        try{
            categoryService.save(category);
            gc.sendMessage("Kategorie wurde erfolgreich gespeichert !", "error");
        }catch(Exception e){
            gc.sendMessage("Kategorie konnte nicht gespeichert werden !", "error");
        }
            
            category.setName(null);
            category.setForAll(false);
            init();
    }
    
    public void delete(Category category){
        try{
            categoryService.delete(category.getId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Kategorie wurde erfolgreich entfernt!"));
        }catch(Exception e){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Achtung", "Eine Kategorie kann nur dann gelöscht werden wenn sie nicht mehr einem Produkt zugeordnet ist!"));
        }
        init();
    }
    
    public void deleteSelected(){
        delete(selectedCategory);
    }
    
    public void onRowSelect(SelectEvent event){
        selectedCategory = (Category) event.getObject();
        System.out.println("selected: " + selectedCategory.getId());
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