package com.forall.laundry.controller.filter;

import com.forall.laundry.controller.UserController;
import com.forall.laundry.model.Customer;
import com.forall.laundry.model.Ordery;
import com.forall.laundry.service.CustomerService;
import com.forall.laundry.service.OrderyService;
import org.primefaces.context.RequestContext;
import org.primefaces.event.TabChangeEvent;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by jd on 8/17/15.
 */
@Named
@RequestScoped
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
        command = "Heute";
        filter();
    }

    public void onTabChange(TabChangeEvent event){
        command = event.getTab().getTitle();
        filter();
    }

    private void filter(){

        switch(command){
            case "Heute":
                //orders = orderyService.getTodaysOrders();
                customers = customerService.getTodaysCustomers();

                break;
            case "Diese Woche":
                //orders = orderyService.getThisWeeksOrders();

                final Calendar cal = Calendar.getInstance();
                cal.setTime(new Date());
                Date today = new Date();
                final long now = today.getTime();
                final long then = now - 1000 * 60 * 60 * 24 * 7;

                Date weekBefore = new Date(then);

                customers = customerService.getCustomersFrom(weekBefore, today);
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
