package com.forall.laundry.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by jd on 9/21/15.
 */
@Entity
public class History implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Basic
    private Integer amount;

    @OneToOne
    private Worker worker;

    public long getId() {
        return id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }
}
