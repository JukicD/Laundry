package com.forall.laundry.model;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    @Basic
    private Date date;

    @OneToOne
    private Worker worker;

    @Transient
    private String formatedDate;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
        SimpleDateFormat format = new SimpleDateFormat("kk:mm");
        formatedDate = format.format(this.date);
    }

    public String getFormatedDate(){
        return formatedDate;
    }
}
