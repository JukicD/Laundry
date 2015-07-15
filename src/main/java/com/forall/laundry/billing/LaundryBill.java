/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.billing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class LaundryBill {
    
    public static void main(String[] args) {
        try {
            
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/laundry","jd", "p1l1o1k1");
            
            String path = "/home/jd/Desktop/test.pdf";
            
            JasperDesign design = JRXmlLoader.load("/home/jd/NetBeansProjects/Laundry/src/main/java/com/forall/laundry/model/Rechnung.jrxml");
            JasperReport report = JasperCompileManager.compileReport(design);
            JasperPrint print = JasperFillManager.fillReport(report, null, con);
            
            JasperExportManager.exportReportToPdfFile(print, path);
            
            
        } catch (SQLException | JRException ex) {
            Logger.getLogger(LaundryBill.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}