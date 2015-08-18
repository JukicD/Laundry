package com.forall.laundry.controller.filter;

import com.forall.laundry.controller.UserController;
import com.forall.laundry.model.Customer;
import com.forall.laundry.model.Ordery;
import com.forall.laundry.service.CustomerService;
import com.forall.laundry.service.OrderyService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * Created by jd on 8/17/15.
 */
@Named
@ViewScoped
public class EventOrdersFilter implements Serializable{

    @Inject
    private UserController userController;

    @EJB
    private OrderyService orderyService;

    @EJB
    private CustomerService customerService;

    private String command;

    private List<Ordery> orders;

    private List<Customer> customers;

    @PostConstruct
    public void init(){

    }

    public void filter(){
        System.out.println(command);
        switch(command){
            case "today":
                //orders = orderyService.getTodaysOrdersFrom(userController.getCustomer());
                //customers = customerService.getTodaysCustomers();
                System.out.println(orders.size());
                break;
            case "week":
                //orders = orderyService.getThisWeeksOrders();
                //customers = customerService.getThisWeeksCustomers();
                break;
            case "month":
                //orders = orderyService.getThisMonthsOrders(); break;
                //customers = customerService.getThisMonthsCustomers(); break;
            case "custom":
                break;
        }
    }

    public List<Ordery> getOrders() {
        return orders;
    }

    public void setOrders(List<Ordery> orders) {
        this.orders = orders;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }
}
