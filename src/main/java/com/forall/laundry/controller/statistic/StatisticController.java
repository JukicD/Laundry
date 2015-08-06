/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.forall.laundry.controller.statistic;

import com.forall.laundry.model.Customer;
import com.forall.laundry.service.CustomerService;
import com.forall.laundry.service.StatisticService;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.BarChartSeries;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;

/**
 *
 * @author jd
 */
@Named
@ViewScoped
public class StatisticController implements Serializable{
    
    @EJB
    private StatisticService statisticService;
    
    @EJB
    private CustomerService customerService;
    
    private Date from;
    
    private Date to;
    
    private LineChartModel model;
    
    private BarChartModel model2;
    
    private boolean grossProfitEntered;
    
    private boolean customerProfitEntered;
    
    
    public void onTabChange(TabChangeEvent event){
        System.out.println(event.getTab().getId());
        switch (event.getTab().getId()) {
            case "grossProfit":
                customerProfitEntered = false;
                grossProfitEntered = true;
                createLineChart();
                break;
            case "customerProfit":
                customerProfitEntered = true;
                grossProfitEntered = false;
                createBarChart();
                break;
        }
    }
    public void createLineChart(){
        
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        model = new LineChartModel();
        ChartSeries series = new ChartSeries();
        
        Calendar calFrom = Calendar.getInstance();
        calFrom.setTime(from);
        
        Calendar calTo = Calendar.getInstance();
        calFrom.setTime(to);
        
        int monthFrom = calFrom.get(Calendar.MONTH);
        int monthTo = calTo.get(Calendar.MONTH);
        
        int yearFrom = calFrom.get(Calendar.YEAR);
        int yearTo = calFrom.get(Calendar.YEAR);
        
        series.setLabel("Umsatz");
        
        for(int i = monthFrom; i <= monthTo; i++){
            
            BigDecimal monthSum = statisticService.getSumFromMonth(i, yearTo);
            System.out.println(monthSum.setScale(2, RoundingMode.HALF_UP));
            series.set(format.format(calFrom.getTime()), monthSum.setScale(2, RoundingMode.HALF_UP));
        }
        
        model.addSeries(series);
       
        model.setZoom(true);
        model.getAxis(AxisType.Y).setLabel("Euro");
        DateAxis axis = new DateAxis("Datum");
        
        axis.setTickAngle(-50);
        axis.setTickFormat("%b %y");

        model.getAxes().put(AxisType.X, axis);

    }
    
    public void createBarChart(){
        System.out.println("bar");
        model2 = new BarChartModel();
        
        ChartSeries series = new BarChartSeries();
        
        series.setLabel("Alle Kunden");
        
        List<Customer> customers = customerService.getAllCustomers();
        
        customers.stream().forEach((customer) -> {
            series.set(customer.getName(), statisticService.getSumFromCustomer(customer).setScale(2, RoundingMode.HALF_UP));
        });
        
        Axis xAxis = model2.getAxis(AxisType.X);
        xAxis.setTickAngle(-50);
        model2.getAxes().put(AxisType.X, xAxis);
        model2.addSeries(series);
    }
    
    public Date getFrom() {
        return from;
    }
    
    public void setFrom(Date from) {
        this.from = from;
    }
    
    public Date getTo() {
        return to;
    }
    
    public void setTo(Date date){
        this.to = date;
    }
    
    public LineChartModel getModel() {
        return model;
    }
    
    public void setModel(LineChartModel model) {
        this.model = model;
    }

    public BarChartModel getModel2() {
        return model2;
    }

    public void setModel2(BarChartModel model2) {
        this.model2 = model2;
    }

    public boolean isGrossProfitEntered() {
        return grossProfitEntered;
    }

    public void setGrossProfitEntered(boolean grossProfitEntered) {
        this.grossProfitEntered = grossProfitEntered;
    }

    public boolean isCustomerProfitEntered() {
        return customerProfitEntered;
    }

    public void setCustomerProfitEntered(boolean customerProfitEntered) {
        this.customerProfitEntered = customerProfitEntered;
    }
    
    
    
}
