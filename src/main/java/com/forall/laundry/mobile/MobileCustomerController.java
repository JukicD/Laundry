package com.forall.laundry.mobile;

import com.forall.laundry.controller.filter.MobileAutoCompleteFilter;
import com.forall.laundry.model.Customer;
import com.forall.laundry.model.Ordery;
import com.forall.laundry.service.CustomerService;
import com.forall.laundry.service.OrderyService;
import org.primefaces.context.RequestContext;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created by jd on 8/20/15.
 */
@Named
@RequestScoped
public class MobileCustomerController implements Serializable{

    @Inject
    private Customer customer;

    @Inject
    private CustomerAutoCompleteFilter mac;

    @EJB
    private CustomerService customerService;

    @EJB
    private OrderyService orderyService;

    public String createCustomer(){
        Ordery ordery = new Ordery();
        ordery.setCustomer(customer);

        customerService.save(customer);
        orderyService.save(ordery);
        customer.setName(null);
        mac.reset();
        return "pm:second?transition=flip";
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
