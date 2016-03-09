package com.forall.laundry.mobile;

import com.forall.laundry.controller.BillingController;
import com.forall.laundry.model.*;
import com.forall.laundry.service.*;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;
import org.primefaces.context.RequestContext;
import singletons.TreeViewSingleton;

/**
 * Created by jd on 8/24/15.
 */
@Named
@RequestScoped
public class MobileProductController implements Serializable{

    @Inject
    private MobileController mc;

    @EJB
    private ProductService productService;

    @EJB
    private CustomerService customerService;

    @EJB
    private PriceService priceService;

    @EJB
    private OrderyService orderyService;

    @EJB
    private CategoryService categoryService;

    @EJB
    private PositionService positionService;

    @Inject
    private BillingController billingController;

    @Inject
    private Product product;

    @Inject
    private TreeViewSingleton treeViewSingleton;

    private List<Category> categories;

    @PostConstruct
    public void init(){
        categories = categoryService.getCategoriesFrom(mc.getCustomer());
    }

    public String createProduct(){

        final Customer customer = customerService.findById(mc.getCustomer().getId());
        final Product search = productService.findByName(product.getName());

        if(search != null){
            product = search;
        }else{
            categories.forEach(product::addCategory);
            productService.save(product);
        }

        if(!customer.getPropertyMap().containsKey(product)){
            final Map<Product, Property> map = customer.getPropertyMap();
            Property prop = new Property();

            map.put(product,prop);
            customerService.update(customer);
        }

        if(!product.getPriceMap().containsKey(customer)){
            Map<Customer, Price> map = product.getPriceMap();
            Price price = new Price();
            price.setPrice(new BigDecimal(0.00));
            priceService.save(price);
            map.put(customer, price);
            productService.update(product);
        }

        return "pm:third?transition=flip";
    }

    public void addItem(){
        
        final Customer customer = customerService.findById(mc.getCustomer().getId());
        Ordery currentOrder = orderyService.findById(mc.getCurrentOrder().getId());
        final Worker worker = mc.getWorker();
        final Integer amount = mc.getAmount();
        final Product prod = mc.getProduct();


        //create a price for a customer within a product
        if(!prod.getPriceMap().containsKey(customer)) {

            final Price p = new Price();
            p.setPrice(new BigDecimal(0.00));
            prod.put(customer, p);
            productService.update(prod);

            Property prop = new Property();
            customer.put(prod, prop);
            customerService.update(customer);
        }

        product = productService.findByName(product.getName());

        //if there is no order for today
        if(currentOrder == null){
            currentOrder = new Ordery();
            currentOrder.setDate(new Date());
            currentOrder.setCustomer(customer);
            orderyService.save(currentOrder);
        }

        //if current order has a position with the product-name and price
        //then get the position and add the amount
        Position position = currentOrder.getPosition(prod.getName(), prod.getPriceMap().get(customer));
        if(position != null){

            position.add(worker, amount);
            position.setName(prod.getName());

            //delete a position if the total amount is 0
            if(position.getAmount() < 1){
                currentOrder.remove(position);
                orderyService.update(currentOrder);
            }
            positionService.update(position);

        }else{

            position = new Position();
            position.setProduct(prod);
            position.setSinglePrice(prod.getPriceMap().get(customer).getPrice());

            position.add(worker, amount);
            position.setName(prod.getName());
            currentOrder.add(position);
            orderyService.update(currentOrder);
        }

        if (currentOrder.isPrinted()) {
            billingController.update(currentOrder, mc.getCustomer().getId());
        }

        mc.updateCurrentOrder();
        mc.setAmount(null);
        treeViewSingleton.init();
        RequestContext.getCurrentInstance().update(":oldOrdersTab:closedOrdersTableForm :oldOrdersTab:selectedPositionsTable :closedOrdersTableForm");
    }

    public void add(final Category category){

        if(!categories.contains(category)){
            categories.add(category);
        }else{
            categories.remove(category);
        }
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
