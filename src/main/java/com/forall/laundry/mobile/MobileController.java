package com.forall.laundry.mobile;

import com.forall.laundry.controller.WorkerController;
import com.forall.laundry.model.*;
import com.forall.laundry.service.*;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

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
    private PositionService positionService;

    @EJB
    private ProductService productService;

    @Inject
    private CustomerAutoCompleteFilter cac;

    @Inject
    private WorkerController wc;

    @Inject
    private ProductAutoCompleteFilter pac;

    @Inject
    private MobileCustomerController mcc;

    private Ordery currentOrder;
    private Worker worker;
    private Customer customer;
    private Category category;
    private Product product;
    private boolean hasItems;
    private Integer amount;
    private Date date;
    private Position selectedPosition;

    @PostConstruct
    public void init(){

        currentOrder = customer == null ? null : orderyService.get(new Date(), customer.getId());

        date = new Date();
    }

    public void addItem(){

        //create a price for a customer within a product
        if(!product.getPriceMap().containsKey(customer)){

            final Price p = new Price();
            p.setPrice(new BigDecimal(0.00));
            product.getPriceMap().put(customer, p);
            productService.update(product);
        }

        product = productService.findByName(product.getName());

        //if there is no order for today
        if(currentOrder == null){
            currentOrder = new Ordery();
            currentOrder.setDate(new Date());
            currentOrder.setCustomer(customer);
            orderyService.save(currentOrder);
        }

        //if current order has a position with the product-name
        //then get the position and add the amount
        if(currentOrder.has(product.getName())){

            Position position = currentOrder.getPosition(product.getName());
            position.add(worker, amount);

            //delete a position if the total amount is 0
            if(position.getAmount() < 1){
                currentOrder.getPositions().remove(currentOrder.getPosition(product.getName()));
                orderyService.update(currentOrder);
            }

            positionService.update(position);

        }else{

            final Position pos = new Position();
            pos.setProduct(product);
            pos.setSinglePrice(product.getPriceMap().get(customer).getPrice());
            pos.add(worker, amount);
            currentOrder.add(pos);
        }

        orderyService.update(currentOrder);
        amount = null;
        cac.reset();
        pac.reset();
    }

    public void getItems(final int offsetFromToday){
        if(date == null){
            date = new Date();
        }

        final Calendar cal = Calendar.getInstance();

        cal.setTime(date);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.DAY_OF_MONTH, offsetFromToday);

        final Date offset = new Date(cal.getTimeInMillis());

        final Ordery ordery = orderyService.get(offset, customer.getId());

        try{
            if(ordery == null){
                throw new IllegalArgumentException();
            }

            this.date = offset;
            currentOrder = ordery;
            hasItems = true;
        }catch(IllegalArgumentException e){
            final String message = offsetFromToday < 0 ? "Der Kunde hatte die letzten 5 Tage keine Lieferungen." : "Ich kann nicht in die Zukunft sehen.";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));
            hasItems = false;
        }
    }

    public void selectPosition(final Position position){
        selectedPosition = position;
    }

    public String getFormatedDate(){
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yy");
        return df.format(date);
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

    public Ordery getCurrentOrder() {
        return currentOrder;
    }

    public void setCurrentOrder(Ordery currentOrder) {
        this.currentOrder = currentOrder;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category)
    {
        this.category = category;
    }

    public boolean isHasItems() {
        return hasItems;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Position getSelectedPosition() {
        return selectedPosition;
    }

    public void setSelectedPosition(Position selectedPosition) {
        this.selectedPosition = selectedPosition;
    }
}
