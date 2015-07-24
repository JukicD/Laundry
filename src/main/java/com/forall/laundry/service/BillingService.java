/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.service;

import com.forall.laundry.model.Customer;
import com.forall.laundry.model.Ordery;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

/**
 *
 * @author jd
 */
@Stateless
public class BillingService {
    
    @EJB
    OrderyService orderyService;
    
    public void createBill(Customer customer, List<Ordery> orders) throws JRException{
  
        try {
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/laundry","jd", "p1l1o1k1");
            JasperDesign design = JRXmlLoader.load("/home/jd/NetBeansProjects/Laundry/src/main/java/com/forall/laundry/billing/Bill.jrxml");
            JasperReport report = JasperCompileManager.compileReport(design);
            
            Map parameter = new HashMap();
            System.out.println(getItemIDs(orders).size() + " " + getItemIDs(orders).get(0));
            System.out.println(getItemIDs(orders));
            parameter.put("item_id_list", getItemIDs(orders));
            JasperPrint print = JasperFillManager.fillReport(report, parameter, con);
            
            
            
            String path = "/home/jd/Desktop/wohoo.pdf";
            JasperExportManager.exportReportToPdfFile(print, path);
        } catch (SQLException ex) {
            Logger.getLogger(BillingService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private List<String> getItemIDs(List<Ordery> orderys){
        
        return orderys.parallelStream()
                .map(f -> f.getItems())
                .flatMap(f -> f.stream())
                .map(f -> f.getItem_id())
                .map(f -> f.toString())
                .collect(Collectors.toList());
    }
}
