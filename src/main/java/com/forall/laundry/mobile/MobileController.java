package com.forall.laundry.mobile;

import com.forall.laundry.controller.filter.MobileAutoCompleteFilter;
import com.forall.laundry.controller.selection.OrderySelectionController;
import com.forall.laundry.model.*;
import com.forall.laundry.service.CustomerService;
import com.forall.laundry.service.ItemService;
import com.forall.laundry.service.OrderyService;
import com.forall.laundry.service.PositionService;
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

        Item item = new Item();
        item.setProduct(product);
        item.setSinglePrice(product.getPriceMap().get(customer).getPrice());
        item.setAmount(amount);
        item.setWorker(worker);
        Ordery ordery = mcc.getCurrentOrder();

        if(ordery == null){
            ordery = new Ordery();
            ordery.setDate(new Date());
            ordery.setCustomer(customer);
            orderyService.save(ordery);
            ordery = mcc.getCurrentOrder();
        }


        item.setOrdery(ordery);
        itemService.save(item);

        if(ordery.getPositionMap().containsKey(item)){
            Position position = ordery.getPositionMap().get(item);
            position.add(item);

            if(position.getAmount() < 1){
                ordery.getPositionMap().remove(item);
                orderyService.update(ordery);
            }

            positionService.update(position);

        }else{
            final Position pos = new Position();
            pos.add(item);
            ordery.getPositionMap().put(item, pos);
            positionService.save(pos);
        }

        orderyService.update(ordery);

        amount = null;
        cac.reset();
        pac.reset();
        update();

        currentOrder = orderyService.get(new Date(), customer.getId());
        currentItems = currentOrder.getPositionMap().keySet().stream().sorted((i1, i2) -> i1.getName().compareToIgnoreCase(i2.getName())).collect(Collectors.toList());

    }

    public void getItems(final int offsetFromToday){
        System.out.println(date);
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
        this.date = offset;

        Ordery ordery = orderyService.get(offset, customer.getId());

        try{
            currentItems = ordery
                    .getPositionMap().keySet()
                    .stream()
                    .collect(Collectors.toList());
            hasItems = true;
        }catch(NullPointerException e){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Der Kunde hatte die letzten fünf Tage keine Positionen !"));
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
