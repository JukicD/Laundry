package com.forall.laundry.model;


import com.forall.laundry.service.PositionService;
import org.hibernate.annotations.Type;

import javax.ejb.EJB;
import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

@Entity
@NamedQueries({
    @NamedQuery(name = "Ordery.findAll", query = "SELECT o FROM Ordery o"),
    @NamedQuery(name = "Ordery.findCustomersOrders", query = "SELECT o FROM Ordery o WHERE o.customer.id = :id"),
    @NamedQuery(name = "Ordery.findCustomersOldOrders", query = "SELECT o FROM Ordery o WHERE o.customer.id = :id AND o.date IS NOT NULL")})
public class Ordery implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    @Basic(fetch=FetchType.LAZY)
    @Column(name = "time")
    private Date date;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="customer_id")
    private Customer customer;

    @Basic
    private boolean isPrinted;

    @CollectionTable(name = "ordery_item_position_map")
    @MapKeyJoinColumn(name = "item_id")
    @ManyToMany(fetch = FetchType.EAGER)
    Map<Item, Position> positionMap;
    
    public Date trimedDate(){
        
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        
        return cal.getTime();
    }
    public Ordery() {
        positionMap = new HashMap<>();

    }

    public Long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public boolean isPrinted() {
        return isPrinted;
    }

    public void setIsPrinted(boolean isPrinted) {
        this.isPrinted = isPrinted;
    }

    public Map<Item, Position> getPositionMap() {
        return positionMap;
    }

    public void setPositionMap(Map<Item, Position> positionMap) {
        this.positionMap = positionMap;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
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
        final Ordery other = (Ordery) obj;
        return Objects.equals(this.id, other.id);
    }
}
