package com.forall.laundry.mobile;

import com.forall.laundry.model.Customer;
import com.forall.laundry.model.Product;
import com.forall.laundry.service.CustomerService;
import com.forall.laundry.service.ProductService;

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

    @EJB
    private ProductService productService;

    @EJB
    private CustomerService customerService;

    @Inject
    private Product product;

    public String createProduct(){
        Customer customer = mc.getCustomer();
        customer.addProduct(product);

        productService.save(product);
        customerService.update(customer);

        return "pm:fourth?transition=flip";
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}