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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.model.chart.AxisType;
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
    
    private Date from;
    
    private Date to;
    
    private LineChartModel model;
    
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
        
        model.setTitle("Zoom for Details");
        model.setZoom(true);
        model.getAxis(AxisType.Y).setLabel("Euro");
        DateAxis axis = new DateAxis("Datum");
        
        axis.setTickAngle(-50);
        axis.setTickFormat("%b %y");

        model.getAxes().put(AxisType.X, axis);

    }
    
    public LineChartModel getModel() {
        return model;
    }
    
    public void setModel(LineChartModel model) {
        this.model = model;
    }
}
