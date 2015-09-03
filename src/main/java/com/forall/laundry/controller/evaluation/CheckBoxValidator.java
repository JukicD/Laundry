package com.forall.laundry.controller.evaluation;

import com.forall.laundry.model.Category;
import com.forall.laundry.model.Product;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created by jd on 9/3/15.
 */
@Named
public class CheckBoxValidator implements Serializable{

    private Product product;

    private Category category;

    public boolean hasCategory;

    @PostConstruct
    public void init(){
        hasCategory = product.getCategories().contains(category);
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean isHasCategory() {
        return hasCategory;
    }

    public void setHasCategory(boolean hasCategory) {
        this.hasCategory = hasCategory;
    }
}
