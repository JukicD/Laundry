package com.forall.laundry.controller.filter;

import com.forall.laundry.controller.navigator.MobileNavigationController;
import com.forall.laundry.model.Customer;
import com.forall.laundry.service.CustomerService;
import org.primefaces.event.SelectEvent;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jd on 8/18/15.
 */
@Named
public class MobileAutoCompleteFilter implements Serializable{

    @EJB
    private CustomerService customerService;

    @Inject
    private MobileNavigationController mnc;

    private Customer customer;

    private List<Customer> customers;

    @PostConstruct
    public void init(){
        customers = customerService.getAllCustomers();
    }

    public List<Customer> selectedCustomers(String query){
        List<Customer> selectedCustomers = new ArrayList<>();
        System.out.println(query);
        customers
                .parallelStream()
                .filter(c -> c.getName().toLowerCase().contains(query))
                .forEach(c -> selectedCustomers.add(c));

        return selectedCustomers;
    }

    public void onItemSelect(SelectEvent event){
        System.out.println(event.getObject().toString());
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
