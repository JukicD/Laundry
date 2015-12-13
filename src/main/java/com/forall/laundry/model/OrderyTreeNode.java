/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.laundry.model;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author jd
 */
public class OrderyTreeNode extends DefaultTreeNode{
    
    private Ordery data;
    
    public OrderyTreeNode(Ordery data){
        super(data);
    }
    
    public OrderyTreeNode(Ordery data, TreeNode root){
        super(data, root);
    }
    
    @Override
    public String toString(){
        if(data != null){
            return data.getDate().toString();
        }
        else
        {
            return super.toString();
        }
    }
    
}
