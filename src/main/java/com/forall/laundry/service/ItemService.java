/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.service;

import com.forall.laundry.EntityDAO.ItemDAOImpl;
import com.forall.laundry.model.Item;
import com.forall.laundry.model.Ordery;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author jd
 */
public class ItemService implements Serializable{
    
    @Inject
    private ItemDAOImpl itemDAOImpl;
    
    public void save(Item item){
        itemDAOImpl.saveOrUpdate(item);
    }

    public ItemDAOImpl getItemDAOImpl() {
        return itemDAOImpl;
    }

    public void setItemDAOImpl(ItemDAOImpl itemDAOImpl) {
        this.itemDAOImpl = itemDAOImpl;
    }

    public List<Item> getItemsFrom(Ordery order) {
        return itemDAOImpl.getItemsFrom(order);
    }
}
