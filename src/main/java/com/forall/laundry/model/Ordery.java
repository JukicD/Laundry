package com.forall.laundry.model;


import com.forall.laundry.service.PositionService;
import org.hibernate.annotations.*;

import javax.ejb.EJB;
import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

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
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date date;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="customer_id")
    private Customer customer;

    @Basic
    private boolean isPrinted;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Position> positions;

    public boolean contains(final Position position){

        return positions.contains(position);
    }

    public Position getPosition(final String name, final Price price){
        for (Position position : positions){
            if(position.getName().equals(name) && position.getSinglePrice().equals(price.getPrice())){
                return position;
            }
        }
        return null;
    }

    public void remove(final Position position){
        if(positions.contains(position)){
            positions.remove(position);
        }
    }

    public Ordery() {
        positions = new ArrayList<>();
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

    public List<Position> getPositions() {
        return positions.stream().sorted().collect(Collectors.toList());
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }

    public void add(final Position position){
        if(!positions.contains(position)){
            positions.add(position);
        }
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
