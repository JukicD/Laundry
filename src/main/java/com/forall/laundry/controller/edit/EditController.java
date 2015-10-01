package com.forall.laundry.controller.edit;

import com.forall.laundry.controller.OldOrdersController;
import com.forall.laundry.controller.OrderyController;
import com.forall.laundry.controller.UserController;
import com.forall.laundry.controller.statistic.StatisticController;
import com.forall.laundry.model.*;
import com.forall.laundry.service.PositionService;
import com.forall.laundry.service.PriceService;
import com.forall.laundry.service.ProductService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Named
@RequestScoped
public class EditController implements Serializable{

    @EJB
    private ProductService productService;

    @EJB
    private PriceService priceService;

    @EJB
    private PositionService positionService;

    @Inject
    private OldOrdersController oc;

    @Inject
    private UserController userController;

    @Inject
    private StatisticController statisticController;

    @Inject
    private OldOrdersController oldOrdersController;

    @Inject
    private OrderyController orderyController;

    private BigDecimal newPrice;

    private List<Product> products;

    @PostConstruct
    public void init() {
        products = userController.getProducts();
        products.sort((p1, p2) -> p1.getName().compareToIgnoreCase(p2.getName()));
    }

    public void onCellEdit(Product product) {

        products.sort((p1, p2) -> p1.getName().compareToIgnoreCase(p2.getName()));
    }

    public void editCustomerPrice(final Price price, final Product product){
        System.out.println("TEST " +price.getPrice().intValue());
        if(price.getPrice().intValue() == 0){
            System.out.println("CHECK");
            orderyController.getAllOrdersFrom(userController.getCustomer())
                    .stream()
                    .flatMap(o -> o.getPositions().stream())
                    .filter(p -> p.getName().equals(product.getName()))
                    .forEach( p -> {
                        p.setSinglePrice(newPrice);
                        positionService.update(p);
                    });

        }


        price.setPrice(newPrice);
        priceService.update(price);
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

    public BigDecimal getPrice() {
        return newPrice;
    }

    public void setPrice(BigDecimal price) {
        this.newPrice = price;
    }
}