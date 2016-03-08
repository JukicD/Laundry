package com.forall.laundry.mobile;

import com.forall.laundry.model.Customer;
import com.forall.laundry.service.CustomerService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject; 
import javax.inject.Named;

/**
 * Created by jd on 8/20/15.
 */
@Named
@SessionScoped
public class CustomerAutoCompleteFilter implements Serializable{

    @EJB
    private CustomerService customerService;

    private List<Customer> customers;

    private String query;

    @Inject
    private Customer customer;

    @PostConstruct
    public void init() {
        customers = filterCustomers();
        customers.sort((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()));
    }

    public List<Customer> filterCustomers(){

        if(query == null || query.equals("")){
            return customerService.getAllCustomers();
        }

        List<Customer> filter = new ArrayList<>();

        customers
                .parallelStream()
                .filter(c -> c.getName().toLowerCase().contains(query.toLowerCase()))
                .forEach(c -> filter.add(c));
        filter.sort((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()));

        return filter;
    }

    public String reset(){
        query = null;
        init();
        return "pm:third";
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
