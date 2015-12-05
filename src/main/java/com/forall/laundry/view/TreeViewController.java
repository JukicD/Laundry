package com.forall.laundry.view;

import com.forall.laundry.controller.UserController;
import com.forall.laundry.controller.filter.OldOrdersFilter;
import com.forall.laundry.model.Bil;
import com.forall.laundry.model.Ordery;
import com.forall.laundry.model.Position;
import com.forall.laundry.model.Product;
import com.forall.laundry.service.BilService;
import com.forall.laundry.service.BillingService;
import com.forall.laundry.service.PositionService;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.TreeNode;
import singletons.TreeViewSingleton;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by jd on 10/14/15.
 */
@Named
@SessionScoped
public class TreeViewController implements Serializable {

    private TreeNode root;
    private TreeNode selectedNode;
    private List<Ordery> selectedOrders;
    private List<Ordery> ordersFromDate;
    private Bil bil;
    private StreamedContent pdf;

    @EJB
    private TreeViewSingleton treeViewSingleton;

    @EJB
    private BilService bilService;

    @EJB
    private BillingService billingService;

    @EJB
    private PositionService positionService;

    @Inject
    private UserController userController;

    @Inject
    private OldOrdersFilter oldOrdersFilter;

    @PostConstruct
    public void init() {
        root = treeViewSingleton.getNodeMap().get(userController.getCustomer());
    }

    public void onNodeSelect(){
        if(selectedNode != null && selectedNode.isLeaf()){

            final int day = Integer.parseInt((String)selectedNode.getData());
            final String month = (String) selectedNode.getParent().getData();
            final int year = Integer.parseInt((String)selectedNode.getParent().getParent().getData());

            int intMonth = -1;
            try {
                Date date = new SimpleDateFormat("MMMM", Locale.GERMAN).parse(month);
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                intMonth = cal.get(Calendar.MONTH) + 1;
            } catch (ParseException e) {
                e.printStackTrace();
            }
            bil = bilService.get(day, intMonth, year, userController.getCustomer());

            ordersFromDate = bil.getOrders();
            selectedOrders = bil.getOrders();
            sortOrders();
        }
    }

    public void showPreview(){
        byte[] data = billingService.createBill(selectedOrders);
        pdf = new DefaultStreamedContent(new ByteArrayInputStream(data));
    }

    public void resetOrders(){
        init();
        selectedNode = null;
        ordersFromDate = null;
        selectedOrders = null;
    }

    public void select(final Ordery order){
        selectedOrders.add(order);
        sortOrders();
    }

    public void unSelect(final Ordery order){
        selectedOrders.remove(order);
        sortOrders();
    }

    public void onCellEdit(final CellEditEvent event){

        String source = event.getColumn().getHeaderText();
        FacesContext context = FacesContext.getCurrentInstance();
        Position pos = context.getApplication().evaluateExpressionGet(context, "#{pos}", Position.class);
        switch(source){
            case "Anzahl": pos.setAmount((Integer)event.getNewValue()); break;
            case "Einzelpreis": pos.setSinglePrice((BigDecimal)event.getNewValue()); break;
        }

        positionService.update(pos);
    }

    private void sortOrders() {
        selectedOrders.stream().sorted((o1, o2) -> o1.getDate().compareTo(o2.getDate()));
    }

    public TreeNode getRoot() {
        return root;
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public List<Ordery> getSelectedOrders() {
        return selectedOrders;
    }

    public List<Ordery> getOrdersFromDate() {
        return ordersFromDate;
    }

    public void setSelectedOrders(List<Ordery> selectedOrders) {
        this.selectedOrders = selectedOrders;
    }

    public Bil getBil() {
        return bil;
    }

    public StreamedContent getPdf() {
        return pdf;
    }
}
