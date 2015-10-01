/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.forall.laundry.service;

import com.forall.laundry.logger.AppLogger;
import com.forall.laundry.model.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.RollbackException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author jd
 */
@Stateless
public class BillingService {
    
    @EJB
    OrderyService orderyService;
    
    @PersistenceContext
    EntityManager em;
    
    @Inject
    AppLogger logger;
    
    public void save(Bill bill){

        try{
            em.persist(bill);
            logger.info("Bill Saved!");
        }catch (RollbackException e){
            logger.error("ERROR ! Bill was not saved !");
        }
    }
    
    public void update(Bill bill){
         try{
            em.merge(bill);
            logger.info("Bill updated!");
        }catch (RollbackException e){
            logger.error("ERROR ! Bill was not updated !");
        }
    }
    
    public Bill getBill(){
        Bill bill;
        try{
            bill = (Bill) em.createQuery("Select b FROM Bill b").setMaxResults(1).getSingleResult();
        }catch (NoResultException e){
            return null;
        }
       
        return bill;
    }
    
    
    public void createBill(Customer customer, List<Ordery> orders) {
        assert(!orders.isEmpty());

        try {
            if(orders.isEmpty()){
                throw new IllegalArgumentException();
            }


            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/laundry","jd", "p1l1o1k1");
            JasperDesign design = JRXmlLoader.load("/home/jd/IdeaProjects/Laundry/src/main/java/com/forall/laundry/billing/Bill.jrxml");
            JasperReport report = JasperCompileManager.compileReport(design);
            
            Map<String, Object> parameter = new HashMap<>();
            parameter.put("item_id_list", getPositionIds(orders));
            parameter.put("customer_id", orders.get(0).getCustomer().getId());
            JasperPrint print = JasperFillManager.fillReport(report, parameter, con);
            
            String path = "/home/jd/Desktop/wohoo.pdf";
            JasperExportManager.exportReportToPdfFile(print, path);


        } catch (SQLException | JRException ex) {
            Logger.getLogger(BillingService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException e){
            logger.error("Illegal Argument in " + BillingService.class.getName() + ". Details: " + e.getCause());
        }
    }
    
    private List<String> getPositionIds(List<Ordery> orderys){
        
        return orderys
                    .parallelStream()
                    .map(Ordery::getPositions)
                    .flatMap(Collection::stream)
                    .map(Position::getId)
                    .map(Object::toString)
                    .collect(Collectors.toList());
    }
}
