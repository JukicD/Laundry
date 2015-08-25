package com.forall.laundry.mobile;

import com.forall.laundry.controller.filter.MobileAutoCompleteFilter;
import com.forall.laundry.controller.selection.OrderySelectionController;
import com.forall.laundry.model.*;
import com.forall.laundry.service.CustomerService;
import com.forall.laundry.service.ItemService;
import com.forall.laundry.service.OrderyService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * Created by jd on 8/19/15.
 */
@Named
@SessionScoped
public class MobileController implements Serializable{

    @EJB
    private OrderyService orderyService;

    @EJB
    private CustomerService customerService;

    @EJB
    private ItemService itemService;

    @Inject
    private CustomerAutoCompleteFilter cac;

    @Inject
    private ProductAutoCompleteFilter pac;

    @Inject
    private MobileCustomerController mcc;

    private List<Item> currentItems;

    private Worker worker;

    private Customer customer;

    private Product product;

    private boolean borrowed;

    private Integer amount;

    public void addItem(){

        Item item = new Item();
        item.setSinglePrice(product.getPrice());
        System.out.println("PRODUCT: " +product.getPrice());
        item.setItem_product(product);
        item.setAmount(amount);
        Ordery ordery = mcc.getCurrentOrder();

        item.setOrdery(ordery);
        ordery.addItem(item);

        itemService.save(item);
        orderyService.update(ordery);
        customerService.update(customer);

        amount = null;
        cac.reset();
        pac.reset();
        update();


        currentItems = customerService.getItems(customer);
    }

    public void update(){
        currentItems = customerService.getItems(customer);
    }


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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Item> getCurrentItems() {
        return currentItems;
    }

    public void setCurrentItems(List<Item> currentItems) {
        this.currentItems = currentItems;
    }
}
