package com.forall.laundry.mobile;

import com.forall.laundry.model.Item;
import com.forall.laundry.service.ItemService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jd on 8/21/15.
 */
@Named
@RequestScoped
public class ItemAutoCompleteFilter implements Serializable{

    @EJB
    private ItemService itemService;

    private List<Item> items;

    private String query;

    @Inject
    private MobileController mc;

    @PostConstruct
    public void init(){
        items = filterItems();
    }

    public List<Item> filterItems(){
        if(mc.getCustomer() == null){
            return null;
        }
        System.out.println(mc.getCustomer().getName());
        if(query == null || query.equals("")){

            return items = itemService.getAllItems(mc.getCustomer());
        }

        List<Item> filter = new ArrayList<>();

        items
                .parallelStream()
                .filter(c -> c.getName().toLowerCase().contains(query.toLowerCase()))
                .forEach(c -> filter.add(c));
        filter.sort((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()));

        return filter;
    }

    public void reset(){
        query = null;
        init();
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
