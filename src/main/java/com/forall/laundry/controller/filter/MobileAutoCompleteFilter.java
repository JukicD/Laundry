package com.forall.laundry.controller.filter;

import com.forall.laundry.controller.UserController;
import com.forall.laundry.controller.navigator.MobileNavigationController;
import com.forall.laundry.model.Customer;
import com.forall.laundry.model.Item;
import com.forall.laundry.service.CustomerService;
import com.forall.laundry.service.ItemService;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.FacesComponent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jd on 8/18/15.
 */
@Named
@RequestScoped
public class MobileAutoCompleteFilter implements Serializable{

    @EJB
    private CustomerService customerService;

    @EJB
    private ItemService itemService;

    @Inject
    private MobileNavigationController mnc;

    private List<Customer> customers;

    private List<Item> items;

    private List<Customer> filteredCustomers;

    private List<Item> filteredItems;

    private Customer customer;

    private String queryCustomer;

    private String queryItems;

    @PostConstruct
    public void init(){

        customers = customerService.getAllCustomers();
    }

    public void filterCustomers(){
        filteredCustomers = new ArrayList<>();
        System.out.println(queryCustomer);
        customers
                .parallelStream()
                .filter(c -> c.getName().toLowerCase().contains(queryCustomer))
                .forEach(c -> filteredCustomers.add(c));

        RequestContext.getCurrentInstance().update("second:outputForm:outputCustomers");
    }

    public void filterItems(){
        filteredItems = new ArrayList<>();
        items
                .parallelStream()
                .filter(i -> i.getItem_product().getName().toLowerCase().contains(queryItems))
                .forEach(i -> filteredItems.add(i));

        RequestContext.getCurrentInstance().update("second:outputForm:outputCustomers");
    }

    public void fillItemsFromCustomer(Customer customer){
        items = itemService.getAllItems(customer);
        System.out.println(items.size());
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
        System.out.println(customer.getName());
        this.customer = customer;
        items = itemService.getAllItems(customer);
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

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<Item> getFilteredItems() {
        return filteredItems;
    }

    public void setFilteredItems(List<Item> filteredItems) {
        this.filteredItems = filteredItems;
    }
}
