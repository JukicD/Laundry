/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.controller.edit;

import com.forall.laundry.controller.OldOrdersController;
import com.forall.laundry.model.Item;
import com.forall.laundry.service.ItemService;
import com.forall.laundry.service.ProductService;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.CellEditEvent;

/**
 *
 * @author jd
 */
@Named
@ViewScoped
public class EditController implements Serializable{
    
    @EJB
    private ItemService itemService;
    
    @EJB
    private ProductService productService;
    
    @Inject
    private OldOrdersController oc;
    
    private List<Item> oldItems;
    
    private List<Item> newItems;
    
    public EditController(){
        
    }
    @PostConstruct
    public void init(){
        oldItems = itemService.getItemsFrom(oc.getOrder());
        newItems = itemService.getItemsFrom(oc.getOrder());
    }
    
    public void updateData(CellEditEvent event) {
        try {
            FacesContext.getCurrentInstance()
                    .getExternalContext().redirect("/pages/orders.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(EditController.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("TEST");
    }

    public ItemService getItemService() {
        return itemService;
    }

    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
    }

    public OldOrdersController getOc() {
        return oc;
    }

    public void setOc(OldOrdersController oc) {
        this.oc = oc;
    }

    public List<Item> getOldItems() {
        return oldItems;
    }

    public void setOldItems(List<Item> oldItems) {
        this.oldItems = oldItems;
    }

    public List<Item> getNewItems() {
        return newItems;
    }

    public void setNewItems(List<Item> newItems) {
        this.newItems = newItems;
    }

    public ProductService getProductService() {
        return productService;
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }
    
    
    
}
