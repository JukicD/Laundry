package com.forall.laundry.controller;

import com.forall.laundry.model.Category;
import com.forall.laundry.service.CategoryService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * Created by jd on 9/2/15.
 */
@Named
@RequestScoped
public class CategoryController implements Serializable{

    @EJB
    private CategoryService categoryService;

    @Inject
    private Category category;

    private List<Category> categories;

    private List<Category> selectedCategories;

    @PostConstruct
    public void init(){
        categories = categoryService.getCategories();
    }

    public void update(Category category){
        categoryService.update(category);
    }

    public void save(){
        categoryService.save(category);
        category.setName(null);
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
}
