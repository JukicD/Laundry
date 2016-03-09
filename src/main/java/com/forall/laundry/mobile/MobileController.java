package com.forall.laundry.mobile;

import com.forall.laundry.model.*;
import com.forall.laundry.service.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/** 
 * Created by jd on 8/19/15.
 */
@Named
@SessionScoped
public class MobileController implements Serializable{

    @EJB
    private OrderyService orderyService;

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

        currentOrder = customer == null ? null : orderyService.get(new Date(), customer.getId(),0);

        if(customer != null && currentOrder == null){
            currentOrder = new Ordery();
            currentOrder.setCustomer(customer);
            currentOrder.setDate(new Date());
            orderyService.save(currentOrder);
        }
        date = new Date();
    }

    public void getItems(final int offsetFromToday){
        if(date == null){
            date = new Date();
        }
        System.out.println(date + " " + offsetFromToday);
        final Calendar cal = Calendar.getInstance();

        cal.setTime(date);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.DAY_OF_MONTH, offsetFromToday);

        final Date offset = new Date(cal.getTimeInMillis());

        final Ordery ordery = orderyService.get(offset, customer.getId(), offsetFromToday);

        try{
            if(ordery == null){
                throw new IllegalArgumentException();
            }

            this.date = ordery.getDate();
            currentOrder = ordery;
            hasItems = true;
        }catch(IllegalArgumentException e){
            final String message = offsetFromToday < 0 ? "Der Kunde hatte die letzten 5 Tage keine Lieferungen." : "Ich kann nicht in die Zukunft sehen.";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));
            hasItems = false;
        }
    }

    public void reset(){
        currentOrder = null;
    }

    public void updateCurrentOrder(){
        currentOrder = orderyService.get(currentOrder.getDate(), customer.getId(), 0);
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
        init();
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
