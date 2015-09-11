package com.forall.laundry.mobile;

import com.forall.laundry.model.*;
import com.forall.laundry.service.CustomerService;
import com.forall.laundry.service.PriceService;
import com.forall.laundry.service.ProductService;
import com.forall.laundry.service.PropertyService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by jd on 8/24/15.
 */
@Named
@ViewScoped
public class MobileProductController implements Serializable{

    @Inject
    private MobileController mc;

    @Inject
    private ProductAutoCompleteFilter pac;

    @EJB
    private ProductService productService;

    @EJB
    private CustomerService customerService;

    @EJB
    private PropertyService propertyService;

    @EJB
    private PriceService priceService;

    private Product product;

    private Set<Category> categories;


    @PostConstruct
    public void init(){
        categories = new HashSet<>();
        product = new Product();
    }
    public String createProduct(){
        final Customer customer = customerService.findById(mc.getCustomer().getId());

        final Property prop = new Property();
        prop.getCategories().addAll(categories);
        customer.add(product, prop);

        final Price p = new Price();
        p.setPrice(new BigDecimal(0));

        priceService.save(p);
        product.getPriceMap().put(customer, p);

        productService.save(product);
        propertyService.save(prop);
        customerService.update(customer);

        pac.init();
        init();
        return "pm:fourth?transition=flip";
    }

    public void add(final Category category){
        if(!categories.contains(category)){
            categories.add(category);
        }else{
            categories.remove(category);
        }
        System.out.println("SIZE: " + categories.size());
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }
}
