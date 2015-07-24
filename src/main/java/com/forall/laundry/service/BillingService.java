/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.service;

import com.forall.laundry.model.Customer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    
    public byte[] getBill(Customer customer) throws JRException{
        
        try {
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/laundry","jd", "p1l1o1k1");
            JasperDesign design = JRXmlLoader.load("/home/jd/NetBeansProjects/Laundry/src/main/java/com/forall/laundry/billing/Bill.jrxml");
            JasperReport report = JasperCompileManager.compileReport(design);
            
            Map parameter = new HashMap();
            parameter.put("customer_id_param", customer.getId());
            JasperPrint print = JasperFillManager.fillReport(report, parameter, con);
            
          
            return  JasperExportManager.exportReportToPdf(print);
            
        } catch (SQLException ex) {
            Logger.getLogger(BillingService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
}
