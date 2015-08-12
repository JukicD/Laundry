/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.forall.laundry.controller.statistic;

import com.forall.laundry.model.Customer;
import com.forall.laundry.model.Item;
import com.forall.laundry.service.CustomerService;
import com.forall.laundry.service.ItemService;
import com.forall.laundry.service.StatisticService;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.event.ItemSelectEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.BarChartSeries;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

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
    
    @EJB
    private ItemService itemService;
    
    private Date from;
    
    private Date to;
    
    private LineChartModel grossModel;
    
    private BarChartModel customerModel;
    
    private BarChartModel specificCustomerModel;
    
    private LineChartModel specificItemFromCustomerModel;
    
    private LineChartModel allItemsFromCustomerModel;
    
    private boolean grossProfitEntered;
    
    private boolean customerProfitEntered;
    
    private boolean customerItemSelected;
    
    private Customer selectedCustomer;
    
    private List<Item> specificItems;
    
    private String itemName;
    
    public void onTabChange(TabChangeEvent event){
        switch (event.getTab().getId()) {
            case "grossProfit":
                customerProfitEntered = false;
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
        grossModel = new LineChartModel();
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
            series.set(format.format(calFrom.getTime()), monthSum.setScale(2, RoundingMode.HALF_UP));
            
            BigDecimal addition = new BigDecimal(233);
            calFrom.add(Calendar.MONTH, 1);
            series.set(format.format(calFrom.getTime()), addition.setScale(2, RoundingMode.HALF_UP));
            
            addition = new BigDecimal(123);
            calFrom.add(Calendar.MONTH, 1);
            series.set(format.format(calFrom.getTime()), addition.setScale(2, RoundingMode.HALF_UP));
            
            addition = new BigDecimal(666);
            calFrom.add(Calendar.MONTH, 1);
            series.set(format.format(calFrom.getTime()), addition.setScale(2, RoundingMode.HALF_UP));
            
            addition = new BigDecimal(333);
            calFrom.add(Calendar.MONTH, 1);
            series.set(format.format(calFrom.getTime()), addition.setScale(2, RoundingMode.HALF_UP));
 
        }
        
        grossModel.addSeries(series);
        grossModel.setZoom(true);
        grossModel.getAxis(AxisType.Y).setLabel("Euro");
        
        CategoryAxis axis = new CategoryAxis("Datum");
        axis.setTickFormat("%b %y");

        grossModel.getAxes().put(AxisType.X, axis);
    }
    
    public void createBarChart(){
        
        customerModel = new BarChartModel();
        
        ChartSeries series = new BarChartSeries();
        
        series.setLabel("Alle Kunden");
        
        List<Customer> customers = customerService.getAllCustomers();
        
        customers
                .stream()
                .sorted((c1, c2) -> statisticService.getSumFromCustomer(c2).intValue() - statisticService.getSumFromCustomer(c1).intValue())
                .forEach( customer -> 
                {
                   series.set(customer.getName(), statisticService.getSumFromCustomer(customer).setScale(2, RoundingMode.HALF_UP));
                });
        
        Axis xAxis = customerModel.getAxis(AxisType.X);
        xAxis.setTickAngle(-50);
        Axis yAxis = customerModel.getAxis(AxisType.Y);
        yAxis.setLabel("Euro");
        customerModel.getAxes().put(AxisType.X, xAxis);
        customerModel.getAxes().put(AxisType.Y, yAxis);
        customerModel.addSeries(series);
    }
    
    public void customerSelect(ItemSelectEvent event){
        
        selectedCustomer = customerService.findByName(customerModel.getTicks().get(event.getItemIndex()));
        createSpecificCustomerModel();
    }
    
    public void itemSelect(ItemSelectEvent event){
        
        
        itemName = specificCustomerModel.getTicks().get(event.getItemIndex());
        createLineChartForSpecificItemFromCustomer();
        customerItemSelected = true;
    }
    
    public void update(){
        createBarChart();
        createSpecificCustomerModel();
        createLineChartForSpecificItemFromCustomer();
    }
    
    private void createLineChartForSpecificItemFromCustomer(){
        
        specificItems = itemService.getSpecificItems(itemName, selectedCustomer);
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yy");
        specificItemFromCustomerModel = new LineChartModel();
        ChartSeries series = new ChartSeries();
       
        
        series.setLabel("Umsatz");
        
        specificItems.stream().forEach((item) -> {
            series.set(format.format(item.getOrdery().getDate()), item.getSum().setScale(2, RoundingMode.HALF_UP));
        });
        
        specificItemFromCustomerModel.addSeries(series);
        specificItemFromCustomerModel.setZoom(true);
        specificItemFromCustomerModel.getAxis(AxisType.Y).setLabel("Euro");
        
        CategoryAxis axis = new CategoryAxis("Datum");
        axis.setTickFormat("%d %b %y");

        specificItemFromCustomerModel.getAxes().put(AxisType.X, axis);
        //RequestContext.getCurrentInstance().update("specificItemDialog");
    }
    
    private void createSpecificCustomerModel(){
        specificCustomerModel = new BarChartModel();
        
        ChartSeries series = new BarChartSeries();
        series.setLabel(selectedCustomer.getName());
        
        
        
        List<Map.Entry<String, BigDecimal>> sums = itemService.getTotalSum(selectedCustomer);

        IntStream
                .iterate(0, n -> n+1)
                .limit(sums.size())
                .forEach(n -> 
                {
                    series.set(sums.get(n).getKey(), sums.get(n).getValue());
                });
        
        Axis xAxis = specificCustomerModel.getAxis(AxisType.X);
        xAxis.setTickAngle(-50);
        Axis yAxis = specificCustomerModel.getAxis(AxisType.Y);
        yAxis.setLabel("Euro");
        specificCustomerModel.getAxes().put(AxisType.X, xAxis);
        specificCustomerModel.getAxes().put(AxisType.Y, yAxis);
        specificCustomerModel.addSeries(series);
    }
    
    public void allItems(){
        allItemsFromCustomerModel = new LineChartModel();
        final SimpleDateFormat format = new SimpleDateFormat("dd.MM.yy");
        
       
        List<Item> items = itemService.getAllItems(selectedCustomer);
        ChartSeries series = new LineChartSeries();
        System.out.println("ITEMS: " + items.size());
        items.stream().forEach( (Item i) -> series.set(format.format(i.getOrdery().getDate()), i.getSum().setScale(2, RoundingMode.HALF_UP)));
        items.forEach((Item i) -> System.out.println(i.getOrdery().getCustomer().getName() + " " + i.getItem_product().getName() + " " + i.getOrdery().getDate()));
        
//        Map<String, List<Item>> test = items.parallelStream().collect(Collectors.groupingBy(i -> i.getItem_product().getName()));
//        
//        test.keySet().stream()
//                .forEach( (String key) ->
//        {
//            ChartSeries series = new LineChartSeries();
//            
//            test.get(key)
//                    .stream()
//                    .sorted((Item i1, Item i2) -> i1.getOrdery().getDate().compareTo(i2.getOrdery().getDate()))
//                    .forEach((Item i) -> 
//                        {
//                            System.out.println("ITEM NAME: " + i.getName() + " ITEM DATE: " + i.getOrdery().getDate());
//                            series.set(format.format(i.getOrdery().getDate()), i.getSum().setScale(2, RoundingMode.HALF_UP));
//                        });
//            
//            series.setLabel(key);
//            allItemsFromCustomerModel.addSeries(series);
//        });
        series.getData().entrySet().stream().forEach((Map.Entry<Object, Number> e) -> System.out.println("KEY: " + e.getKey() + " Value: " + e.getValue()));
        allItemsFromCustomerModel.addSeries(series);
        allItemsFromCustomerModel.setLegendPosition("e");
        allItemsFromCustomerModel.getAxis(AxisType.Y).setLabel("Euro");
        CategoryAxis axis = new CategoryAxis("Datum");
        
        axis.setTickFormat("%d %b %y");
        allItemsFromCustomerModel.getAxes().put(AxisType.X, axis);
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
        return grossModel;
    }
    
    public void setGrossModel(LineChartModel model) {
        this.grossModel = model;
    }

    public BarChartModel getCustomerModel() {
        return customerModel;
    }

    public void setCustomerModel(BarChartModel model2) {
        this.customerModel = model2;
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

    public BarChartModel getSpecificCustomerModel() {
        return specificCustomerModel;
    }

    public void setSpecificCustomerModel(BarChartModel specificCustomerModel) {
        this.specificCustomerModel = specificCustomerModel;
    }

    public Customer getSelectedCustomer() {
        return selectedCustomer;
    }

    public void setSelectedCustomer(Customer selectedCustomer) {
        this.selectedCustomer = selectedCustomer;
    }

    public LineChartModel getSpecificItemFromCustomerModel() {
        return specificItemFromCustomerModel;
    }

    public void setSpecificItemFromCustomerModel(LineChartModel specificItemFromCustomerModel) {
        this.specificItemFromCustomerModel = specificItemFromCustomerModel;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public boolean isCustomerItemSelected() {
        return customerItemSelected;
    }

    public void setCustomerItemSelected(boolean customerItemSelected) {
        this.customerItemSelected = customerItemSelected;
    }

    public LineChartModel getAllItemsFromCustomerModel() {
        return allItemsFromCustomerModel;
    }

    public void setAllItemsFromCustomerModel(LineChartModel allItemsFromCustomerModel) {
        this.allItemsFromCustomerModel = allItemsFromCustomerModel;
    }   
}
