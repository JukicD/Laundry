package com.forall.laundry.mobile;

import com.forall.laundry.controller.filter.MobileAutoCompleteFilter;
import com.forall.laundry.model.Customer;
import com.forall.laundry.model.Worker;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created by jd on 8/19/15.
 */
@Named
@SessionScoped
public class MobileController implements Serializable{

    @Inject
    private MobileAutoCompleteFilter mac;

    private Worker worker;

    private Customer customer;

    private boolean borrowed;

    private Integer amount;


    public String goTo(String nav){
        return nav;
    }
    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
        System.out.println(this.customer);
    }

    public boolean isBorrowed() {
        return borrowed;
    }

    public void setBorrowed(boolean borrowed) {
        this.borrowed = borrowed;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
