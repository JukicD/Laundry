/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.controller;

import com.forall.laundry.logger.AppLogger;
import com.forall.laundry.mobile.CustomerAutoCompleteFilter;
import com.forall.laundry.model.Customer;
import com.forall.laundry.model.Price;
import com.forall.laundry.service.CustomerService;
import com.forall.laundry.service.OrderyService;
import com.forall.laundry.service.PriceService;
import com.forall.laundry.service.ProductService;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author jd
 */
@Named
@RequestScoped
public class CustomerController implements Serializable{

    @EJB
    private CustomerService customerService;

    @EJB
    private OrderyService orderyService;

    @EJB
    private ProductService productService;

    @EJB
    private PriceService priceService;

    @Inject
    private Customer customer;

    @Inject
    private CustomerAutoCompleteFilter customerAutoCompleteFilter;

    @Inject
    private AppLogger logger;

    public void createCustomer(){

        try{
            customerService.save(customer);
            productService.getProducts().stream().forEach(p ->
            {
                final Price price = new Price();
                price.setPrice(new BigDecimal(0.00));
                priceService.save(price);
                p.getPriceMap().put(customer, price);
                productService.update(p);
            });

            logger.info("CUSTOMER CREATED: " +customer.toString());
            final String message = "Kunde wurde erfolgreich erstellt.";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,message, ""));
        }catch (Exception e){
            logger.error("FAILURE SAVING: " + customer.toString() + " Cause: " + e.getCause());
            FacesMessage errorMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Es ist ein Fehler aufgetreten.", "Kunde wurde nicht gespeichert.");
            FacesContext.getCurrentInstance().addMessage(null, errorMsg);
        }finally{
            customer.setAddress(null);
            customer.setCompanyName(null);
            customer.setPhoneNumber(null);
            customer.setZipCode(null);
            customer.setCustomerNumber(0);
            customer.setMailAddress(null);
            customer.setName(null);
        }
        customerAutoCompleteFilter.init();
    }

    public void update(){
        try{
            customerService.update(customer);
            logger.info("Customer " + customer.getName() + " was updated!");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("", "Der Kunde wurde erfolgreich aktualisiert."));
        }catch (Exception e){
            logger.error("Update Failed ! " + customer.getName());
        }
    }
    
    public void delete(Customer customer){
        try{
            if(!orderyService.hasOpenOrders(customer)){
                customerService.delete(customer);
            }
        }catch(Exception e){
            logger.info("Deleting Customer failed! " + e.getMessage());
        }
    }

    public List<Customer> getAllCustomers(){
        return customerService.getAllCustomers();
    }
    public CustomerService getCustomerService() {
        return customerService;
    }

    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public OrderyService getOrderyService() {
        return orderyService;
    }

    public void setOrderyService(OrderyService orderyService) {
        this.orderyService = orderyService;
    }

    public AppLogger getLogger() {
        return logger;
    }

    public void setLogger(AppLogger logger) {
        this.logger = logger;
    }
}
