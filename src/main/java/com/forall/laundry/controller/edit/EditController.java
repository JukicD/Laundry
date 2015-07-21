package com.forall.laundry.controller.edit;

import com.forall.laundry.controller.OldOrdersController;
import com.forall.laundry.model.Item;
import com.forall.laundry.model.Product;
import com.forall.laundry.service.ItemService;
import com.forall.laundry.service.ProductService;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class EditController implements Serializable{
    
    @EJB
    private ItemService itemService;
    
    @EJB
    private ProductService productService;
    
    @Inject
    private OldOrdersController oc;
    
    private List<Item> items;
    
    @PostConstruct
    public void init(){
        items = itemService.getItemsFrom(oc.getOrder());
    }
    
    public void onCellEdit(Item item) {
        
        Item i = itemService.find(item.getItem_id());
        Product p = i.getItem_product();
        p.setName(item.getName());
        System.out.println("TEST " + p.getName());
        i.setItem_product(p);
        productService.update(p);
        
        
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
}