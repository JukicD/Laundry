/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.controller;

import com.forall.laundry.model.Ordery;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jd
 */
@Named
@RequestScoped
public class OldOrdersController implements Serializable{
    
    @Inject
    private Ordery order;
    
    public void setup(Ordery order){
        order.getPositions().stream().forEach(p -> System.out.println(p.getName()));
        this.order = order;
    }
    
    
    public Ordery getOrder() {
        return order;
    }

    public void setOrder(Ordery order) {
        this.order = order;
    }

   public void getBill(Ordery o){
        
        PreparedStatement stmt = null;
        final String sql = "SELECT \"public\".ordery.bill FROM \"public\".ordery WHERE \"public\".ordery.order_id = ?";
        
        try {
            final Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/laundry", "jd", "p1l1o1k1");
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, o.getId());
            
            final ResultSet set = stmt.executeQuery();
          
            if(set.next()){
               
                final InputStream in = set.getBinaryStream("bill");
                final OutputStream out = new FileOutputStream(new File("/home/jd/Desktop/wohoo.pdf"));
                
                int bytesRead = -1;
                final byte[] buffer = new byte[512];
                
                while((bytesRead = in.read(buffer)) != -1){
                    out.write(buffer, 0, bytesRead);
                }
                in.close();
                out.close();
            }
            
        } catch (SQLException | IOException ex) {
            Logger.getLogger(OldOrdersController.class.getName()).log(Level.SEVERE, null, ex);
        }

   }
}
