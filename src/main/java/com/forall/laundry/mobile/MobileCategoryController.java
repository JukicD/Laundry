package com.forall.laundry.mobile;

import com.forall.laundry.model.Category;
import com.forall.laundry.model.Customer;
import com.forall.laundry.service.CategoryService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by jd on 9/8/15.
 */
@Named
@RequestScoped
public class MobileCategoryController implements Serializable {

    @EJB
    private CategoryService categoryService;

    @Inject
    private MobileController mobileController;

    private List<Category> categories;

    @PostConstruct
    public void init(){
        categories = categoryService.getCategoriesFrom(mobileController.getCustomer());
        if(categories != null){
            categories.sort((c1, c2) -> c1.isForAll() ? c2.isForAll() ? c1.getName().compareToIgnoreCase(c2.getName()) : -1 : 1);
        }
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
