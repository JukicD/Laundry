/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.service;

import com.forall.laundry.logger.AppLogger;
import com.forall.laundry.model.Customer;
import java.util.List;
import javax.ejb.EJB;
import net.sf.jasperreports.engine.JRException;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
/**
 *
 * @author jd
 */
@RunWith(Arquillian.class)
public class CustomerServiceTest {
    
    @EJB
    private CustomerService customerService;
    
    @Deployment
    public static WebArchive createDeployment() {
        
        WebArchive wa = ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackage(CustomerService.class.getPackage())
                .addPackage(Customer.class.getPackage())
                .addPackage(JRException.class.getPackage())
                .addPackage(AppLogger.class.getPackage())
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
        
        System.out.println(wa.toString(true));
        return wa;
    }
    
    @Test
    public void test1(){
        
        List<Customer> customers = customerService.getAllCustomers();
        
        Assert.assertEquals(3, customers.size());
    }
}
