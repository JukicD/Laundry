package com.forall.laundry.mobile;

import com.forall.laundry.model.*;
import com.forall.laundry.service.*;
import com.forall.laundry.view.CustomerMainView;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by jd on 8/24/15.
 */
@Named
@RequestScoped
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

    @EJB
    private OrderyService orderyService;

    @EJB
    private CategoryService categoryService;

    private Product product;

    @Inject
    private MobileCategoryController mcc;

    private Set<Category> categories;

    @PostConstruct
    public void init(){
        categories = new HashSet<>();
        product = new Product();
    }

    public String createProduct(){

        final Customer customer = customerService.findById(mc.getCustomer().getId());
        Product search = productService.findByName(product.getName());

        if(search != null){
            product = search;
        }else{
            categories.stream().forEach(product::addCategory);
            productService.save(product);
        }

        Product p = productService.findByName(product.getName());

        Price price = new Price();
        price.setPrice(new BigDecimal(0.00));
        priceService.save(price);

        Property prop = new Property();

        customer.getPropertyMap().put(p, prop);
        customerService.update(customer);

        p.getPriceMap().put(customer, price);
        propertyService.save(prop);

        productService.update(product);

        return "pm:third?transition=flip";
    }

    public void add(final Category category){
        System.out.println(category);
        if(!categories.contains(category)){
            categories.add(category);
            System.out.println(categories);
        }else{
            categories.remove(category);
        }
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
