/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.controller;

import com.forall.laundry.model.Item;
import com.forall.laundry.model.Ordery;
import com.forall.laundry.service.ItemService;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author jd
 */
@Named
@SessionScoped
public class OldOrdersController implements Serializable{
    
    @Inject
    private Ordery order;
    
    @EJB
    private ItemService itemService;
    
    @PersistenceContext
    EntityManager em;
    
    public List<Item> getItems(){
        return itemService.getItemsFrom(order);
    }
    
    public void setup(Ordery order){
        this.order = order;
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/pages/orders.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(OldOrdersController.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    
    public Ordery getOrder() {
        return order;
    }

    public void setOrder(Ordery order) {
        this.order = order;
    }

    public ItemService getItemService() {
        return itemService;
    }

    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
    }

    public void getBill(Ordery o){
        
        System.out.println("RECHNUNG WIRD GEDRUCKT!" + o.getOrder_id());
        PreparedStatement stmt = null;
        String sql = "SELECT \"public\".ordery.bill FROM \"public\".ordery WHERE \"public\".ordery.order_id = ?";
        
        try {
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/laundry", "postgres", "p1l1o1k1");
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, o.getOrder_id());
            
            ResultSet set = stmt.executeQuery();
          
            if(set.next()){
               
                InputStream in = set.getBinaryStream("bill");
                OutputStream out = new FileOutputStream(new File("/home/jd/Desktop/wohoo.pdf"));
                
                int bytesRead = -1;
                byte[] buffer = new byte[512];
                
                while((bytesRead = in.read(buffer)) != -1){
                    out.write(buffer, 0, bytesRead);
                }
                in.close();
                out.close();
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(OldOrdersController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(OldOrdersController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(OldOrdersController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
    
    
}
