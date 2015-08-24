package com.forall.laundry.controller.edit;

import com.forall.laundry.controller.OldOrdersController;
import com.forall.laundry.controller.UserController;
import com.forall.laundry.controller.statistic.StatisticController;
import com.forall.laundry.model.Item;
import com.forall.laundry.model.Product;
import com.forall.laundry.service.ItemService;
import com.forall.laundry.service.ProductService;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

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
    
    @Inject
    private StatisticController statisticController;
    
    private List<Product> products;

    private List<Item> items;
    
    @PostConstruct
    public void init(){

       products = userController.getProducts();
        products.sort((p1 ,p2) -> p1.getName().compareToIgnoreCase(p2.getName()));
        items = oc.getItems();
    }
    
    public void onCellEdit(Product product) {
        productService.update(product);
        products.sort((p1, p2) -> p1.getName().compareToIgnoreCase(p2.getName()));
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public UserController getUserController() {
        return userController;
    }

    public void setUserController(UserController userController) {
        this.userController = userController;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}