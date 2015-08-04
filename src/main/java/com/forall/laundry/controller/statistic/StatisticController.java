/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.controller.statistic;

import com.forall.laundry.service.StatisticService;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.BarChartSeries;

/**
 *
 * @author jd
 */
@Named
@ViewScoped
public class StatisticController implements Serializable{
   
    @EJB
    private StatisticService statisticService;
    
    private Date from;
    
    private Date to;
    
    private BarChartModel model;
    
    private boolean entered;

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        entered = true;
        this.to = to;
    }

    public boolean isEntered() {
        return entered;
    }

    public void setEntered(boolean entered) {
        this.entered = true;
    }

    public void createLineChart(){
        
        model = new BarChartModel();
        Calendar calFrom = Calendar.getInstance();
        calFrom.setTime(from);
        int monthFrom = calFrom.get(Calendar.MONTH);
        int yearFrom = calFrom.get(Calendar.YEAR);
        
        Calendar calTo = Calendar.getInstance();
        calTo.setTime(to);
        int monthTo = calTo.get(Calendar.MONTH);
        int yearTo = calTo.get(Calendar.YEAR);
        
        
        BarChartSeries series = new BarChartSeries();
        System.out.println(monthFrom + " " + monthTo);
        for(int i = monthFrom; i <= monthTo; i++){
            
            BigDecimal monthSum = statisticService.getSumFromMonth(i, yearTo);
            System.out.println(monthSum.setScale(2, RoundingMode.HALF_UP));
            series.set(calFrom.getTime(), monthSum.setScale(2, RoundingMode.HALF_UP));
        }
       model.addSeries(series);
    }

    public BarChartModel getModel() {
        return model;
    }

    public void setModel(BarChartModel model) {
        this.model = model;
    }
}
