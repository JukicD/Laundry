package com.forall.laundry.view;

import com.forall.laundry.controller.BillingController;
import com.forall.laundry.controller.UserController;
import com.forall.laundry.model.Bill;
import com.forall.laundry.model.Ordery;
import com.forall.laundry.model.Position;
import com.forall.laundry.service.BillService;
import com.forall.laundry.service.BillPrintingService;
import com.forall.laundry.service.CustomerService;
import com.forall.laundry.service.OrderyService;
import com.forall.laundry.service.PositionService;
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
import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import org.primefaces.context.RequestContext;
import org.primefaces.event.NodeCollapseEvent;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;

/**
 * Created by jd on 10/14/15.
 */
@Named
@SessionScoped
public class TreeViewController implements Serializable {

    private TreeNode root;
    private TreeNode[] selectedNodes;
    private List<Ordery> selectedOrders;
    private List<Ordery> ordersFromDate;
    private List<Bill> printedBills;
    private StreamedContent pdf;
    private String radioSelection;
    private Date billDate;
    private boolean isLeaf;

    @EJB
    private TreeViewSingleton treeViewSingleton;

    @EJB
    private OrderyService orderyService;

    @EJB
    private BillPrintingService billPrintingService;

    @EJB
    private PositionService positionService;

    @EJB
    private BillService billService;
    
    @EJB
    private CustomerService customerService;

    @Inject
    private UserController userController;

    @Inject
    private BillingController billingController;

    @PostConstruct
    public void init() {
        root = treeViewSingleton.getNodeMap().get(userController.getCustomer());
        ordersFromDate = new ArrayList<>();
        radioSelection = "offen";
    }

    public void onDeliveryNodeSelect() {
        if (selectedNodes != null && selectedNodes.length > 0) {

            ordersFromDate = new ArrayList<>();
            
            Stream.of(selectedNodes).forEach(selectedNode -> {
                StringBuilder b = new StringBuilder();
                TreeNode curNode = selectedNode;

                while (!curNode.getData().equals("root")) {

                    b.append(curNode.getData().toString());
                    curNode = curNode.getParent();
                    if (!curNode.getData().equals("root")) {
                        b.append(".");
                    }
                }

                String date = b.toString();

                if (isLegalDate(date)) {

                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd.MMMM.yyyy", Locale.GERMAN);
                    try {
                        cal.setTime(sdf.parse(date));

                        Ordery order = orderyService.get(cal.getTime(), userController.getCustomer().getId(), 0);

                        if (!ordersFromDate.contains(order)) {
                            ordersFromDate.add(order);
                        }

                    } catch (ParseException ex) {
                        Logger.getLogger(TreeViewController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            selectedOrders = ordersFromDate;
        } else {
            ordersFromDate = new ArrayList<>();
            selectedOrders = new ArrayList<>();
        }
    }

    public void onBillNodeSelect(NodeSelectEvent event) {

        ordersFromDate = null;
        ordersFromDate = new ArrayList<>();
        TreeNode node = event.getTreeNode();
        
        if (node.isLeaf()) {
            isLeaf = true;
            StringBuilder b = new StringBuilder();
            TreeNode curNode = node;

            while (!curNode.getData().equals("root")) {
                
                b.append(curNode.getData().toString());
                curNode = curNode.getParent();
                
                if (!curNode.getData().equals("root")) {
                    b.append(".");
                }
            }

            String date = b.toString();

            if (isLegalDate(date)) {

                Calendar cal = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MMMM.yyyy", Locale.GERMAN);
                try {
                    cal.setTime(sdf.parse(date));

                    printedBills = billService.get(cal.getTime(), userController.getCustomer().getId());
                    billDate = printedBills.get(0).getPrinted();
                    
                } catch (ParseException ex) {
                    Logger.getLogger(TreeViewController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            showBill();
        }else{
            isLeaf = false;
        }
    }

    public void updateOrders() {

        switch (radioSelection) {
            case "offen":
                treeViewSingleton.init();
                break;
            case "alle":
                treeViewSingleton.filterAll();
                break;
        }
        root = treeViewSingleton.getNodeMap().get(userController.getCustomer());
    }

    public void showDeliveryPreview() {
            byte[] data = billPrintingService.createBill(selectedOrders, billingController.getBill().getNumber());
            pdf = new DefaultStreamedContent(new ByteArrayInputStream(data));
    }

    public void showBill(){
        Bill b = billService.get(billDate, userController.getCustomer().getId()).get(0);
        byte[] data = b.getBill();
        pdf = new DefaultStreamedContent(new ByteArrayInputStream(data));
    }

    public void resetOrders() {
        selectedNodes = null;
        ordersFromDate = null;
        selectedOrders = null;
    }

    public void select(final Ordery order) {
        selectedOrders.add(order);
        sortOrders();
    }

    public void unSelect(final Ordery order) {
        selectedOrders.remove(order);
        sortOrders();
    }
    
    public void onRowSelect(SelectEvent event){
        Bill bill = (Bill) event.getObject();
        createPDF(bill.getBill());
        
    }
    
    private void createPDF(byte[] data){
        pdf = new DefaultStreamedContent(new ByteArrayInputStream(data));
    }

    public void onCellEdit(final CellEditEvent event) {

        String source = event.getColumn().getHeaderText();
        FacesContext context = FacesContext.getCurrentInstance();
        Position pos = context.getApplication().evaluateExpressionGet(context, "#{pos}", Position.class);
        switch (source) {
            case "Anzahl":
                pos.setAmount((Integer) event.getNewValue());
                break;
            case "Einzelpreis":
                pos.setSinglePrice((BigDecimal) event.getNewValue());
                break;
        }
        positionService.update(pos);
    }

    public void onTabChange(TabChangeEvent event) {
        resetOrders();
        String title = event.getTab().getTitle();

        switch (title) {
            case "Rechnungen":
                treeViewSingleton.initBills();
                root = treeViewSingleton.getBillRoot();
                RequestContext.getCurrentInstance().update(":oldOrdersTab:printBillsForm :oldOrdersTab:billTableForm :oldOrdersTab:selectedBillsTableForm");
                break;
            case "Lieferungen":
                treeViewSingleton.init();
                root = treeViewSingleton.getDeliveryRoot();
                RequestContext.getCurrentInstance().update(":oldOrdersTab:closedOrdersForm :oldOrdersTab:closedOrdersTableForm :oldOrdersTab:selectedPositionsTable");
                reset();
                break;
        }
    }

    public void deleteSelectedNodes() {
        Stream.of(selectedNodes).parallel().forEach(node -> {
            removeNode(root, node);
        });
    }

    private boolean removeNode(TreeNode root, TreeNode nodeToDelete) {
        if (root.getChildren().remove(nodeToDelete)) {
            return true;
        } else {
            for (TreeNode childNode : root.getChildren()) {
                if (childNode.getChildCount() > 0) {
                    return removeNode(childNode, nodeToDelete);
                }
            }
            return false;
        }
    }
    
    public void reset(){
        selectedOrders = new ArrayList<>();
        isLeaf = false;
        pdf = null;
        printedBills = null;
    }

    public void resetDeliveryRoot() {
        root = treeViewSingleton.getDeliveryRoot();
    }

    private void sortOrders() {
        selectedOrders.stream().sorted((o1, o2) -> o1.getDate().compareTo(o2.getDate()));
    }

    public TreeNode getRoot() {
        return root;
    }

    public TreeNode[] getSelectedNodes() {
        return selectedNodes;
    }

    public void setSelectedNodes(TreeNode[] selectedNodes) {
        this.selectedNodes = selectedNodes;
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

    public List<Bill> getPrintedBills() {
        return printedBills;
    }
    
    public StreamedContent getPdf() {
        return pdf;
    }

    public String getRadioSelection() {
        return radioSelection;
    }

    public void setRadioSelection(String radioSelection) {
        this.radioSelection = radioSelection;
    }

    private boolean isLegalDate(String s) {
        SimpleDateFormat f = new SimpleDateFormat("dd.MMMM.yyyy", Locale.GERMAN);
        f.setLenient(false);
        return f.parse(s, new ParsePosition(0)) != null;
    }

    public void nodeExpand(NodeExpandEvent event) {
        event.getTreeNode().setExpanded(true);
    }

    public void nodeCollapse(NodeCollapseEvent event) {
        event.getTreeNode().setExpanded(true);
    }

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    public boolean isIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(boolean isLeaf) {
        this.isLeaf = isLeaf;
    }
}
