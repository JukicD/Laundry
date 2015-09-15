package com.forall.laundry.mobile;

import com.forall.laundry.model.*;
import com.forall.laundry.service.*;

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

    @EJB
    private OrderyService orderyService;

    private Product product;

    private Set<Category> categories;


    @PostConstruct
    public void init(){
        categories = new HashSet<>();
        product = new Product();
    }

    public String createProduct(){

        final Customer customer = customerService.findById(mc.getCustomer().getId());
        final Product p = productService.findByName(product.getName());

        final Price pr = new Price();
        pr.setPrice(new BigDecimal(0));
        priceService.save(pr);

        if(p == null){ // product does not exist (by name) use the injected information

            product.setCategories(categories);
            product.getPriceMap().put(customer, pr);
            productService.save(product);

        }else{ // use the product from database
            product = p;
        }

        product.getPriceMap().put(customer, pr);
        final Property prop = new Property();
        prop.getCategories().addAll(categories);
        customer.add(product, prop);

        propertyService.save(prop);
        productService.update(product);
        customerService.update(customer);

        pac.init();
        init();
        return "pm:third?transition=flip";
    }

    public void add(final Category category){
        if(!categories.contains(category)){
            categories.add(category);
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
