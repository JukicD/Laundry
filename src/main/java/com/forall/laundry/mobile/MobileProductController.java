package com.forall.laundry.mobile;

import com.forall.laundry.model.Customer;
import com.forall.laundry.model.Product;
import com.forall.laundry.model.Property;
import com.forall.laundry.service.CustomerService;
import com.forall.laundry.service.ProductService;
import com.forall.laundry.service.PropertyService;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

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

    @Inject
    private Product product;

    public String createProduct(){
        final Customer customer = customerService.findById(mc.getCustomer().getId());
        product.setBorrowed(mc.isBorrowed());
        final Property prop = new Property();
        customer.add(product, prop);

        productService.save(product);
        propertyService.save(prop);
        customerService.update(customer);

        pac.init();

        return "pm:fourth?transition=flip";
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
