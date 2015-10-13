/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.controller.filter;

import com.forall.laundry.controller.OrderyController;
import com.forall.laundry.model.Ordery;
import com.forall.laundry.util.LaundryUtil;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author jd
 */
@Named
@RequestScoped
public class OldOrdersFilter implements Serializable{

    @Inject
    private OrderyController orderyController;

    private String command;
    private String select;
    private Date dateFrom;
    private Date dateTo;
    private List<Ordery> orders;
    private List<Ordery> todaysOrders;
    private boolean isChoosingDate;
    private List<Ordery> openBills;
    private List<Ordery> closedBills;
    private List<Ordery> selectedOrders;

    @PostConstruct
    public void init(){
        orders = orderyController.getThisMonthsOrdersFromCurrentCustomer();
        todaysOrders = orders.stream().filter( o -> LaundryUtil.isToday(o.getDate())).collect(Collectors.toList());
        openBills = orders.stream().filter( o -> !o.isPrinted()).collect(Collectors.toList());
        closedBills = orders.stream().filter(Ordery::isPrinted).collect(Collectors.toList());
        System.out.println("INIT");
    }

    public void filter(){
        switch(command){
            case "all":
                orders = orderyController.getOldOrders();
                dateFrom = null;
                dateTo = null;
                isChoosingDate = false;
                break;
            case "recent":
                orders = orderyController.getThisMonthsOrdersFromCurrentCustomer();
                dateFrom = null;
                dateTo = null;
                isChoosingDate = false;
                break;
            case "date":
                isChoosingDate = true;

                if(dateFrom != null && dateTo != null){

                    orders = orderyController.getOrdersFrom(dateFrom, dateTo);
                    isChoosingDate = false;
                    break;
                }
        }
    }

    public void onTabChange(final TabChangeEvent event){
        String title = event.getTab().getTitle();

        switch(title){
            case "Offene Rechnungen": orders = orders.stream().filter( o -> !o.isPrinted()).collect(Collectors.toList()); break;
            case "Alte Rechnungen": orders = orders.stream().filter(Ordery::isPrinted).collect(Collectors.toList()); break;
        }
    }

    public void onClose() {
        isChoosingDate = false;
    }

    public void filterOrders(SelectEvent event){

    }

    public List<Ordery> getTodaysOrders() {
        return todaysOrders;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getSelect() {
        return select;
    }

    public void setSelect(String select) {
        this.select = select;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public List<Ordery> getOrders() {
        return orders;
    }

    public void setOrders(List<Ordery> orders) {
        this.orders = orders;
    }

    public boolean isChoosingDate() {
        return isChoosingDate;
    }

    public void setIsChoosingDate(boolean isChoosingDate) {
        this.isChoosingDate = isChoosingDate;
    }

    public List<Ordery> getOpenBills() {
        return openBills;
    }

    public List<Ordery> getClosedBills() {
        return closedBills;
    }

    public List<Ordery> getSelectedOrders() {
        return selectedOrders;
    }

    public void setSelectedOrders(List<Ordery> selectedOrders) {
        this.selectedOrders = selectedOrders;
    }
}
