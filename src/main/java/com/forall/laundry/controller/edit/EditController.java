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
import org.primefaces.event.TabChangeEvent;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

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

    private List<Product> ownProducts;

    private List<Product> rentProducts;

    private List<Item> items;

    @PostConstruct
    public void init(){

        products = userController.getProducts();
        ownProducts = products.parallelStream().filter( p -> !p.isBorrowed()).collect(Collectors.toList());
        ownProducts.sort((p1 ,p2) -> p1.getName().compareToIgnoreCase(p2.getName()));

        ownProducts.stream().forEach( p -> System.out.println(p.getPrice()));
        rentProducts = products.parallelStream().filter( p -> p.isBorrowed()).collect(Collectors.toList());
        rentProducts.sort((p1 ,p2) -> p1.getName().compareToIgnoreCase(p2.getName()));

        products.sort((p1 ,p2) -> p1.getName().compareToIgnoreCase(p2.getName()));
        items = oc.getItems();
    }

    public void onCellEdit(Product product) {
        productService.update(product);
        Item item = itemService.findItemWithProductID(product.getProduct_id());
        item.setSinglePrice(product.getPrice());
        itemService.update(item);
        products.sort((p1, p2) -> p1.getName().compareToIgnoreCase(p2.getName()));
    }

    public void onItemEdit(Item item){

        if(item.getSinglePrice() == null){
            item.setSinglePrice(item.getItem_product().getPrice());
        }
        System.out.println("EDIT");
        itemService.update(item);
    }

    public void onTabChange(TabChangeEvent event){
        switch(event.getTab().getId()){
            case("rentTab"):
                products = products.parallelStream().filter( p -> !p.isBorrowed()).collect(Collectors.toList());
                products.sort((p1 ,p2) -> p1.getName().compareToIgnoreCase(p2.getName()));
                break;
            case("ownTab"):
                products = products.parallelStream().filter( p -> p.isBorrowed()).collect(Collectors.toList());
                products.sort((p1 ,p2) -> p1.getName().compareToIgnoreCase(p2.getName()));
                break;
        }
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

    public List<Product> getOwnProducts() {
        return ownProducts;
    }

    public void setOwnProducts(List<Product> ownProducts) {
        this.ownProducts = ownProducts;
    }

    public List<Product> getRentProducts() {
        return rentProducts;
    }

    public void setRentProducts(List<Product> rentProducts) {
        this.rentProducts = rentProducts;
    }

}