package com.forall.laundry.mobile;

import com.forall.laundry.controller.filter.MobileAutoCompleteFilter;
import com.forall.laundry.model.Category;
import com.forall.laundry.service.CategoryService;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by jd on 9/8/15.
 */
@Named
@SessionScoped
public class MobileCategoryController implements Serializable {

    @EJB
    private CategoryService categoryService;

    @Inject
    private MobileController mobileController;
    
    @Inject
    private MobileAutoCompleteFilter mac;

    private List<Category> categories;

    @PostConstruct
    public void init(){
        long start = System.currentTimeMillis();
        System.out.println("INIT START");
        categories = categoryService.getCategoriesFrom(mobileController.getCustomer());
        if(categories != null){
            categories.sort((c1, c2) -> c1.isForAll() ? c2.isForAll() ? c1.getName().compareToIgnoreCase(c2.getName()) : -1 : 1);
        }
        System.out.println("INIT END " + (System.currentTimeMillis() - start));
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}