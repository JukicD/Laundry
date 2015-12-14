package singletons;

import com.forall.laundry.controller.UserController;
import com.forall.laundry.model.Bil;
import com.forall.laundry.model.Customer;
import com.forall.laundry.model.Ordery;
import com.forall.laundry.service.BilService;
import com.forall.laundry.service.CustomerService;
import com.forall.laundry.service.OrderyService;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import javax.annotation.PostConstruct;
import javax.ejb.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;
import javax.inject.Inject;

/**
 * Created by jd on 10/14/15.
 */
@Singleton
@Startup
public class TreeViewSingleton implements Serializable {

    @EJB
    private CustomerService customerService;

    @EJB
    private BilService bilService;

    @EJB
    private OrderyService orderyService;
    
    @Inject
    private UserController userController;

    private Map<Customer, TreeNode> deliveryRootMap;
    
    private Map<Customer, TreeNode> billRootMap;

    @PostConstruct
    @Asynchronous
    public void init() {
        long start = System.currentTimeMillis();
        deliveryRootMap = new HashMap<>();
        List<Customer> customers = customerService.getAllCustomers();

        customers
            .stream()
            .forEach( customer -> 
            {
            final List<Ordery> orders = orderyService.getOrdersFrom(customer);
            final List<String> dates = new ArrayList<>();

            orders
            .stream()
            .filter(order -> !order.isPrinted())
            .forEach(order -> 
                    dates.add(parseDate(order.getDate()))
            );
            deliveryRootMap = createNodes(deliveryRootMap, dates, customer);
            });
        System.out.println("Time: " + (System.currentTimeMillis() - start));
    }

    private String parseDate(final Date date) {
        final SimpleDateFormat f = new SimpleDateFormat("dd.MMMM.yyyy", Locale.GERMAN);
        return f.format(date);
    }

    private Map<Customer, TreeNode> createNodes(Map<Customer, TreeNode> rootMap, final List<String> dates, final Customer customer) {

        dates
                .stream()
                .forEach(d -> {

                    final String[] ds = d.split(Pattern.quote("."));
                    final String yearS = ds[2];
                    final String monthS = ds[1];
                    final String dayS = ds[0];
                    final TreeNode root = rootMap.containsKey(customer) ? rootMap.get(customer) : new DefaultTreeNode("root", null);

                    final TreeNode year = contains(root, yearS) == null ? new DefaultTreeNode(yearS, root) : contains(root, yearS);
                    final TreeNode month = contains(year, monthS) == null ? new DefaultTreeNode(monthS, year) : contains(year, monthS);
                    final TreeNode day = contains(month, dayS) == null ? new DefaultTreeNode(dayS, month) : contains(month, dayS);

                    month.getChildren().add(day);

                    root.getChildren().add(year);
                    rootMap.put(customer, root);
                });
        
        return rootMap;
    }

    public Map<Customer, TreeNode> getNodeMap() {
        return deliveryRootMap;
    }

    private TreeNode contains(final TreeNode node, final String name) {
        for (TreeNode n : node.getChildren()) {
            if (n.getData().equals(name)) {
                return n;
            }
        }
        return null;
    }
    
    public void initBills(){
        billRootMap = new HashMap<>();
        List<Customer> customers = customerService.getAllCustomers();

        customers
            .stream()
            .forEach(
                c -> {

                    final List<Bil> bills = bilService.getBilsFrom(c);
                    final List<String> dates = new ArrayList<>();

                    bills
                    .stream()
                    .forEach(bill -> dates.add(parseDate(bill.getPrinted()))
                    );
                    billRootMap = createNodes(billRootMap, dates, c);
                }
            );
    }

    public void filterAll() {
        long start = System.currentTimeMillis();
        deliveryRootMap = new HashMap<>();
        List<Customer> customers = customerService.getAllCustomers();

        customers
            .stream()
            .forEach(
                c -> {

                    final List<Ordery> orders = orderyService.getOrdersFrom(c);
                    final List<String> dates = new ArrayList<>();
                    orders
                    .stream()
                    .forEach(order -> dates.add(parseDate(order.getDate()))
                    );
                    deliveryRootMap = createNodes(deliveryRootMap, dates, c);
                }
            );
    }
    
    public TreeNode getDeliveryRoot(){
        return deliveryRootMap.get(userController.getCustomer());
    }
    
    public TreeNode getBillRoot(){
        return billRootMap.get(userController.getCustomer());
    }
}
