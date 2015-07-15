/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.controller;

import com.forall.laundry.model.Customer;
import com.forall.laundry.model.Item;
import com.forall.laundry.model.Ordery;
import com.forall.laundry.model.Product;
import com.forall.laundry.service.CustomerService;
import com.forall.laundry.service.ItemService;
import com.forall.laundry.service.OrderyService;
import com.forall.laundry.service.ProductService;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
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
@Named
@RequestScoped
public class OrderyController implements Serializable{
    
    @Inject
    private UserController userController;
    
    @Inject
    private Item item;
    
    @Inject
    private Product product;
    
    @EJB
    private ItemService itemService;
    
    @EJB 
    private ProductService productService;
    
    @EJB
    private OrderyService orderyService;
    
    @EJB
    private CustomerService customerService;
    
    
    public void addItem(){
        
        Customer customer = userController.getCustomer();
        Ordery order = userController.getCurrentOrder();
        
        order.setCustomer(customer);
        
        customerService.update(customer);
        
        item.setOrdery(order);
        item.setItem_product(product);
        order.addItem(item);
        
        productService.save(product);
        itemService.save(item);
        orderyService.save(order);
        
        product.setProductName(null);
        item.setAmount(0);
    }
    
    public void finishOrder(){
        try {
            Ordery order = userController.getCurrentOrder();
            order.setDate(new Date());
            
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/laundry","jd", "p1l1o1k1");
             JasperDesign design = JRXmlLoader.load("/home/jd/NetBeansProjects/Laundry/src/main/java/com/forall/laundry/model/Rechnung.jrxml");
            JasperReport report = JasperCompileManager.compileReport(design);
            JasperPrint print = JasperFillManager.fillReport(report, null, con);
            
          
            byte[] bill =  JasperExportManager.exportReportToPdf(print);
            
            order.setBill(bill);
            orderyService.save(order);
            
            Ordery o = new Ordery();
            Customer customer = userController.getCustomer();
            
            o.setCustomer(customer);
         
            customerService.update(customer);
            orderyService.save(o);
        } catch (JRException | SQLException ex) {
            Logger.getLogger(OrderyController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Ordery> getOrders(){
        return orderyService.getOrdersFrom(userController.getCustomer());
    }
    
    public List<Ordery> getOldOrders(){
        return orderyService.getOldOrdersFrom(userController.getCustomer());
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ItemService getItemService() {
        return itemService;
    }

    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
    }

    public ProductService getProductService() {
        return productService;
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public UserController getUserController() {
        return userController;
    }

    public void setUserController(UserController userController) {
        this.userController = userController;
    }

    public OrderyService getOrderyService() {
        return orderyService;
    }

    public void setOrderyService(OrderyService orderyService) {
        this.orderyService = orderyService;
    }

    public CustomerService getCustomerService() {
        return customerService;
    }

    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }
}
