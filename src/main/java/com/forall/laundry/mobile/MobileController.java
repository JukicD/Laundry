package com.forall.laundry.mobile;

import com.forall.laundry.controller.filter.MobileAutoCompleteFilter;
import com.forall.laundry.controller.selection.OrderySelectionController;
import com.forall.laundry.model.*;
import com.forall.laundry.service.CustomerService;
import com.forall.laundry.service.ItemService;
import com.forall.laundry.service.OrderyService;
import com.forall.laundry.util.LaundryUtil;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by jd on 8/19/15.
 */
@Named
@ViewScoped
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
    private Category category;
    private Product product;
    private boolean borrowed;
    private Integer amount;

    @PostConstruct
    public void init(){
        if(customer != null){
            currentItems = orderyService.get(new Date(), customer.getId()).stream().flatMap( o -> o.getItems().stream()).collect(Collectors.toList());
        }
    }
    public void addItem(){

        Item item = new Item();
        item.setItem_product(product);
        item.setAmount(amount);
        Ordery ordery = mcc.getCurrentOrder();

        if(ordery.getDate() == null || !LaundryUtil.isToday(ordery.getDate())){
            ordery.setDate(new Date());
        }

        item.setOrdery(ordery);
        ordery.addItem(item);

        itemService.save(item);
        orderyService.update(ordery);
        customerService.update(customer);

        amount = null;
        cac.reset();
        pac.reset();
        update();

        currentItems = orderyService.get(new Date(), customer.getId()).stream().flatMap( o -> o.getItems().stream()).collect(Collectors.toList());

    }

    public void getYesterdaysItems(){

        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);

        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.DAY_OF_MONTH, -1);

        Date yesterday = new Date(cal.getTimeInMillis());

        currentItems = orderyService.get(yesterday, customer.getId()).stream().flatMap( o -> o.getItems().stream()).collect(Collectors.toList());
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category)
    {
        this.category = category;
    }
}
