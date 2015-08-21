package com.forall.laundry.mobile;

import com.forall.laundry.model.Customer;
import com.forall.laundry.service.CustomerService;
import org.primefaces.context.RequestContext;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
        System.out.println("INIT");
        System.out.println(query);
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
                .filter(c -> c.getName().toLowerCase().contains(query == null ? "" : query.toLowerCase()))
                .forEach(c -> filter.add(c));
        filter.sort((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()));

        return filter;
    }

    public void reset(){
        query = null;
        init();
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
        System.out.println(this.customer);
    }
}
