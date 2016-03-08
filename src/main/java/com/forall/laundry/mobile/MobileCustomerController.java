package com.forall.laundry.mobile;

import com.forall.laundry.model.Customer;
import com.forall.laundry.model.Ordery;
import com.forall.laundry.service.CustomerService;
import com.forall.laundry.service.OrderyService;
import java.io.Serializable;
import java.util.Date;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

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

    @Inject
    private MobileController mc;

    @EJB
    private CustomerService customerService;

    @EJB
    private OrderyService orderyService;

    public String createCustomer(){
        final Ordery ordery = new Ordery();
        ordery.setDate(new Date());
        ordery.setCustomer(customer);

        customerService.save(customer);
        orderyService.save(ordery);
        customer.setName(null);
        mac.reset();
        return "pm:second?transition=flip";
    }

    public Ordery getCurrentOrder(){
        return customerService.getCurrentOrder(mc.getCustomer());
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
