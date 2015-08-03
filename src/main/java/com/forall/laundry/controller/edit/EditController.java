package com.forall.laundry.controller.edit;

import com.forall.laundry.controller.OldOrdersController;
import com.forall.laundry.controller.UserController;
import com.forall.laundry.model.Item;
import com.forall.laundry.service.ItemService;
import com.forall.laundry.service.ProductService;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class EditController implements Serializable{
    
    @EJB
    private ItemService itemService;
    
    @EJB
    private ProductService productService;
    
    @Inject
    private OldOrdersController oc;
    
    @Inject
    private UserController userController;
    
    private List<Item> items;
    
    @PostConstruct
    public void init(){

       items = oc.getItems();

    }
    
    public void onCellEdit(Item item) {
        
        productService.update(item.getItem_product());
        itemService.update(item);
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

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
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
}