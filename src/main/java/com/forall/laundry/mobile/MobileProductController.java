package com.forall.laundry.mobile;

import com.forall.laundry.model.*;
import com.forall.laundry.service.*;
import com.forall.laundry.view.CustomerMainView;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.criteria.Order;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by jd on 8/24/15.
 */
@Named
@RequestScoped
public class MobileProductController implements Serializable{

    @Inject
    private MobileController mc;

    @Inject
    private ProductAutoCompleteFilter pac;

    @EJB
    private ProductService productService;

    @EJB
    private CustomerService customerService;

    @EJB
    private PropertyService propertyService;

    @EJB
    private PriceService priceService;

    @EJB
    private OrderyService orderyService;

    @EJB
    private CategoryService categoryService;

    @EJB
    private PositionService positionService;

    @Inject
    private Product product;

    @Inject
    private MobileCategoryController mcc;

    private List<Category> categories;

    @PostConstruct
    public void init(){
        categories = categoryService.getCategoriesFrom(mc.getCustomer());
    }

    public String createProduct(){

        final Customer customer = customerService.findById(mc.getCustomer().getId());


        categories.forEach(product::addCategory);
        productService.save(product);

        if(!customer.getPropertyMap().containsKey(product)){
            final Map<Product, Property> map = customer.getPropertyMap();
            Property prop = new Property();

            map.put(product,prop);
            customerService.update(customer);
        }

        return "pm:third?transition=flip";
    }

    public void addItem(){

        final Customer customer = customerService.findById(mc.getCustomer().getId());
        Ordery currentOrder = orderyService.get(new Date(), customer.getId());
        final Worker worker = mc.getWorker();
        final Integer amount = mc.getAmount();
        final Product prod = mc.getProduct();


        //create a price for a customer within a product
        if(!prod.getPriceMap().containsKey(customer)){

            final Price p = new Price();
            p.setPrice(new BigDecimal(0.00));
            prod.put(customer, p);
            productService.update(prod);

            Property prop = new Property();
            customer.put(prod, prop);
            prod.getCategories().stream().forEach(c -> System.out.println(c.getName()));
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

        //if current order has a position with the product-name
        //then get the position and add the amount
        if(currentOrder.has(prod.getName())){

            Position position = currentOrder.getPosition(prod.getName());
            position.add(worker, amount);
            position.setName(prod.getName());

            //delete a position if the total amount is 0
            if(position.getAmount() < 1){
                currentOrder.remove(currentOrder.getPosition(prod.getName()));
                orderyService.update(currentOrder);
            }

            positionService.update(position);

        }else{

            final Position pos = new Position();
            pos.setProduct(prod);
            pos.setSinglePrice(prod.getPriceMap().get(customer).getPrice());

            pos.add(worker, amount);
            pos.setName(prod.getName());
            currentOrder.add(pos);
        }

        orderyService.update(currentOrder);
        mc.updateCurrentOrder();
        mc.setAmount(null);
    }

    public void add(final Category category){
        System.out.println(category);
        if(!categories.contains(category)){
            categories.add(category);
            System.out.println(categories);
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
