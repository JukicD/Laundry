package com.forall.laundry.controller.edit;

import com.forall.laundry.controller.OldOrdersController;
import com.forall.laundry.controller.UserController;
import com.forall.laundry.controller.statistic.StatisticController;
import com.forall.laundry.model.Customer;
import com.forall.laundry.model.Item;
import com.forall.laundry.model.Price;
import com.forall.laundry.model.Product;
import com.forall.laundry.service.ItemService;
import com.forall.laundry.service.PriceService;
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
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Named
@RequestScoped
public class EditController implements Serializable{

    @EJB
    private ItemService itemService;

    @EJB
    private ProductService productService;

    @EJB
    private PriceService priceService;

    @Inject
    private OldOrdersController oc;

    @Inject
    private UserController userController;

    @Inject
    private StatisticController statisticController;

    private BigDecimal newPrice;

    private List<Product> products;
    private List<Item> items;

    @PostConstruct
    public void init(){

        products = userController.getProducts();

        products.sort((p1 ,p2) -> p1.getName().compareToIgnoreCase(p2.getName()));
        items = oc.getItems();
    }

    public void onCellEdit(Product product) {

        products.sort((p1, p2) -> p1.getName().compareToIgnoreCase(p2.getName()));
    }

    public void onItemEdit(Item item){


        itemService.update(item);
    }

    public void editCustomerPrice(Price price, Product product){
        System.out.println(price.getPrice() + " " + product);
        Price p = product.getPriceMap().get(userController.getCustomer());
        p.setPrice(newPrice);
        priceService.update(p);
        product.getPriceMap().put(userController.getCustomer(), p);
        productService.update(product);
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

    public BigDecimal getPrice() {
        return newPrice;
    }

    public void setPrice(BigDecimal price) {
        this.newPrice = price;
    }
}