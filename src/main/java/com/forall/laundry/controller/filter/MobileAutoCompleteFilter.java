package com.forall.laundry.controller.filter;

import com.forall.laundry.controller.navigator.MobileNavigationController;
import com.forall.laundry.model.Customer;
import com.forall.laundry.service.CustomerService;
import org.primefaces.context.RequestContext;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jd on 8/18/15.
 */
@Named
@SessionScoped
public class MobileAutoCompleteFilter implements Serializable{

    @EJB
    private CustomerService customerService;

    @Inject
    private MobileNavigationController mnc;

    private List<Customer> customers;

    private List<Customer> filteredCustomers;

    private Customer customer;

    private String queryCustomer;

    private String queryItems;

    @PostConstruct
    public void init(){
        customers = customerService.getAllCustomers();
        customers.sort((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()));
    }

    public void filterCustomers(){
        filteredCustomers = new ArrayList<>();
        customers
                .parallelStream()
                .filter(c -> c.getName().toLowerCase().contains(queryCustomer == null ? "" : queryCustomer.toLowerCase()))
                .forEach(c -> filteredCustomers.add(c));
        filteredCustomers.sort((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()));
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public List<Customer> getFilteredCustomers() {
        return filteredCustomers;
    }

    public void setFilteredCustomers(List<Customer> filteredCustomers) {
        this.filteredCustomers = filteredCustomers;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getQueryItems() {
        return queryItems;
    }

    public void setQueryItems(String queryItems) {
        this.queryItems = queryItems;
    }

    public String getQueryCustomer() {
        return queryCustomer;
    }

    public void setQueryCustomer(String queryCustomer) {
        this.queryCustomer = queryCustomer;
    }

}
