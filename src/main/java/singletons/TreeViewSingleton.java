package singletons;

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
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

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

    private Map<Customer, TreeNode> nodeMap;

    @PostConstruct
    public void init(){
        long start = System.currentTimeMillis();
        nodeMap = new HashMap<>();
        List<Customer> customers = customerService.getAllCustomers();

        customers
                .stream()
                .forEach(
                        c -> {

                            final List<Bil> bils = bilService.getBilsFrom(c);
                            final List<String> dates = new ArrayList<>();

                            bils
                                    .stream()
                                    .forEach(b -> dates.add(parseDate(b.getPrinted()))
                                    );
                            createNodes(dates, c);
                        }
                );


        Ordery ord = orderyService.findById(2);
        ord.getPositions().stream().forEach( p -> System.out.println(p.getName()));

        System.out.println("Time: " + (System.currentTimeMillis() - start));
    }

    private String parseDate(final Date date){
        final SimpleDateFormat f = new SimpleDateFormat("dd.MMMM.yyyy", Locale.GERMAN);
        return f.format(date);
    }

    private void createNodes(final List<String> dates, final Customer customer){

        dates
                .stream()
                .forEach(d -> {

                    final String[] ds = d.split(Pattern.quote("."));
                    final String yearS = ds[2];
                    final String monthS = ds[1];
                    final String dayS = ds[0];
                    final TreeNode root = nodeMap.containsKey(customer) ? nodeMap.get(customer) : new DefaultTreeNode("root", null);

                    final TreeNode year = contains(root, yearS) == null ? new DefaultTreeNode(yearS, root) : contains(root, yearS);
                    final TreeNode month = contains(year, monthS) == null ? new DefaultTreeNode(monthS, year) : contains(year, monthS);
                    final TreeNode day = contains(month, dayS) == null ? new DefaultTreeNode(dayS, month) : contains(month, dayS);

                    month.getChildren().add(day);

                    root.getChildren().add(year);
                    nodeMap.put(customer, root);
                });
    }

    public Map<Customer, TreeNode> getNodeMap() {
        return nodeMap;
    }

    private TreeNode contains(final TreeNode node, final String name){
        for(TreeNode n : node.getChildren()){
            if(n.getData().equals(name)){
                return n;
            }
        }
        return null;
    }
}
