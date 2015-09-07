package com.forall.laundry.model;

import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.annotation.Generated;
import javax.persistence.*;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by jd on 06.09.15.
 */
@Entity
public class Property implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Category> categories;

    public Property(){
        categories = new HashSet<>();
    }

    public long getId() {
        return id;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void addCategeory(Category category){
        if(categories == null){
            categories = new HashSet<>();
        }

        if(!categories.contains(category)){
            categories.add(category);
        }
    }

    public void removeCategory(Category category){
        if(categories.contains(category)){
            categories.remove(category);
        }
    }

    public boolean contains(Category category){
        return categories.contains(category);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Property property = (Property) o;

        return id == property.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
