package com.forall.laundry.model;


import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

@Entity
@NamedQueries({
        @NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p")
})
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long product_id;

    @ManyToMany( fetch = FetchType.EAGER)
    private Set<Category> categories;

    @CollectionTable(name = "product_customer_price_map")
    @MapKeyJoinColumn(name = "customer_id")
    @ManyToMany(fetch = FetchType.EAGER)
    @Cascade(CascadeType.ALL)
    Map<Customer, Price> priceMap;

    @Basic
    private String name;

    public Product(){
        priceMap = new HashMap<>();
        categories = new HashSet<>();
    }

    public void addCategory(Category category){
        if(categories == null){
            categories = new HashSet<>();
        }
        if(!categories.contains(category)){
            categories.add(category);
        }
    }

    public void put(final Customer customer, final Price price){
        priceMap.put(customer, price);
    }

    public boolean hasCategory(Category category){
        return categories.contains(category);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
   
    public Long getProduct_id() {
        return this.product_id;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {

        this.categories = categories;
    }

    public Map<Customer, Price> getPriceMap() {
        return priceMap;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Product other = (Product) obj;
        return Objects.equals(this.name, other.name);
    }

    @Override
    public String toString() {
        return "Product{" + "product_id=" + product_id + ", name=" + name + '}';
    }


    public void remove(Category category) {
        if(categories.contains(category)){
            categories.remove(category);
        }
    }
}
