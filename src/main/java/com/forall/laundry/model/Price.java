package com.forall.laundry.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by jd on 8/23/15.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "Price.delete", query = "DELETE FROM Price where id = :id")
})
public class Price implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Basic
    private BigDecimal price;

    public Price(){
        price = new BigDecimal(0.00);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
