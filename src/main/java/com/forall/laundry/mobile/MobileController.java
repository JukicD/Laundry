package com.forall.laundry.mobile;

import com.forall.laundry.controller.filter.MobileAutoCompleteFilter;
import com.forall.laundry.controller.selection.OrderySelectionController;
import com.forall.laundry.model.*;
import com.forall.laundry.service.*;
import com.forall.laundry.util.LaundryUtil;
import org.primefaces.context.RequestContext;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by jd on 8/19/15.
 */
@Named
@SessionScoped
public class MobileController implements Serializable{

    @EJB
    private OrderyService orderyService;

    @EJB
    private CustomerService customerService;

    @EJB
    private ItemService itemService;

    @EJB
    private PositionService positionService;

    @EJB
    private ProductService productService;

    @Inject
    private CustomerAutoCompleteFilter cac;

    @Inject
    private ProductAutoCompleteFilter pac;

    @Inject
    private MobileCustomerController mcc;

    private List<Item> currentItems;
    private Ordery currentOrder;
    private Worker worker;
    private Customer customer;
    private Category category;
    private Product product;
    private boolean hasItems;
    private Integer amount;
    private Date date;
    private Position selectedPosition;
    private Item selectedItem;

    @PostConstruct
    public void init(){
        if(customer != null){
            currentItems = orderyService.get(new Date(), customer.getId()).getPositionMap().keySet().stream().collect(Collectors.toList());
            currentItems.sort((i1, i2) -> i1.getName().compareToIgnoreCase(i2.getName()));
            currentOrder = orderyService.get(new Date(), customer.getId());
        }
        date = new Date();
    }
    public void addItem(){

        if(!product.getPriceMap().containsKey(customer)){

            final Price p = new Price();
            product.getPriceMap().put(customer, p);
            productService.update(product);
        }

        product = productService.findByName(product.getName());

        final Item item = new Item();
        item.setProduct(product);
        item.setSinglePrice(product.getPriceMap().get(customer).getPrice());
        item.setAmount(amount);
        item.setWorker(worker);

        if(currentOrder == null){
            currentOrder = new Ordery();
            currentOrder.setDate(new Date());
            currentOrder.setCustomer(customer);
            orderyService.save(currentOrder);
        }

        currentOrder = orderyService.get(new Date(), customer.getId());

        item.setOrdery(currentOrder);
        itemService.save(item);

        if(currentOrder.getPositionMap().containsKey(item)){
            Position position = currentOrder.getPositionMap().get(item);
            position.add(item);

            if(position.getAmount() < 1){
                currentOrder.getPositionMap().remove(item);
                orderyService.update(currentOrder);
            }

            positionService.update(position);

        }else{
            final Position pos = new Position();
            pos.add(item);
            currentOrder.getPositionMap().put(item, pos);
            positionService.save(pos);
        }

        orderyService.update(currentOrder);
        amount = null;
        cac.reset();
        pac.reset();
        update();
        currentItems = currentOrder.getPositionMap().keySet().stream().sorted().collect(Collectors.toList());
    }

    public void getItems(final int offsetFromToday){
        if(date == null){
            date = new Date();
        }
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.DAY_OF_MONTH, offsetFromToday);

        final Date offset = new Date(cal.getTimeInMillis());

        Ordery ordery = orderyService.get(offset, customer.getId());

        try{
            currentItems = ordery
                    .getPositionMap().keySet()
                    .stream()
                    .sorted()
                    .collect(Collectors.toList());
            this.date = offset;
            currentOrder = ordery;
            hasItems = true;
        }catch(NullPointerException e){
            String message = offsetFromToday < 0 ? "Der Kunde hatte die letzten 5 Tage keine Lieferungen." : "Ich kann nicht in die Zukunft sehen.";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));
            hasItems = false;
        }
    }

    public void selectPosition(final Item item){
        selectedPosition = currentOrder.getPositionMap().get(item);
        selectedItem = item;
    }

    public void update(){
        currentItems = customerService.getItems(customer);
    }

    public String getFormatedDate(){
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yy");
        return df.format(date);
    }

    public boolean currentItemsFromToday(){
        return LaundryUtil.isToday(currentItems.get(0).getOrdery().getDate());
    }
    public String goTo(String nav){
        return nav;
    }
    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Ordery getCurrentOrder() {
        return currentOrder;
    }

    public void setCurrentOrder(Ordery currentOrder) {
        this.currentOrder = currentOrder;
    }

    public List<Item> getCurrentItems() {
        return currentItems;
    }

    public void setCurrentItems(List<Item> currentItems) {

        this.currentItems = currentItems;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category)
    {
        this.category = category;
    }

    public boolean isHasItems() {
        return hasItems;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Position getSelectedPosition() {
        return selectedPosition;
    }

    public void setSelectedPosition(Position selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    public Item getSelectedItem() {
        return selectedItem;
    }
}
