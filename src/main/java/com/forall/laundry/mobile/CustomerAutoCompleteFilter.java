package com.forall.laundry.mobile;

import com.forall.laundry.model.Customer;
import com.forall.laundry.service.CustomerService;
import org.primefaces.context.RequestContext;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jd on 8/20/15.
 */
@Named
@RequestScoped
public class CustomerAutoCompleteFilter implements Serializable{

    @EJB
    private CustomerService customerService;

    private List<Customer> customers;

    private List<Customer> filteredCustomers;

    private String query;

    private Customer customer;

    @PostConstruct
    public void init(){
        customers = customerService.getAllCustomers();
        customers.sort((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()));
    }

    public void filterCustomers(){
        filteredCustomers = new ArrayList<>();
        customers
                .parallelStream()
                .filter(c -> c.getName().toLowerCase().contains(query == null ? "" : query.toLowerCase()))
                .forEach(c -> filteredCustomers.add(c));
        filteredCustomers.sort((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()));

        RequestContext.getCurrentInstance().update("second:outputForm");
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
        System.out.println("c" + this.customer);
    }
}
