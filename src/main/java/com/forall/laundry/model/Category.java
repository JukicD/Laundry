package com.forall.laundry.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jd on 9/2/15.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "Category.all", query = "SELECT c FROM Category c")
})
public class Category implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Basic
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Product> products;

    @Basic
    private boolean forAll;

    public void addProduct(Product product){
        if(products == null){
            products = new ArrayList<>();
        }
        if(!products.contains(product)){
            products.add(product);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public List<Product> getProduct() {
        return products;
    }

    public void setProduct(List<Product> product) {
        this.products = product;
    }

    public boolean isForAll() {
        return forAll;
    }

    public void setForAll(boolean forAll) {
        this.forAll = forAll;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;

        return id == category.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
