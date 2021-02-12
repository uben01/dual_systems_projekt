package com.dualsystems.invoices.data.entity;

import com.dualsystems.invoices.data.AbstractEntity;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "INVOICE")
@Table(name = "INVOICE")
public class Invoice extends AbstractEntity {

    @Column(name = "CUSTOMERNAME", nullable = false)
    private String customerName;

    @Temporal(TemporalType.DATE)
    @Column(name = "ISSUEDATE", nullable = false)
    private Date issueDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "DUEDATE", nullable = false)
    private Date dueDate;

    @Column(name = "COMMENT")
    private String comment;

    @OneToMany(mappedBy = "invoice",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Item> items = new ArrayList<>();

    @Transient
    private Integer totalHUF;

    @Transient
    private Double totalEUR;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Integer getTotalHUF() {
        if (totalHUF == null) {
            setTotalHUF();
        }
        return totalHUF;
    }

    public void setTotalHUF() {
        if (totalEUR == null) {
            setTotalEUR();
        }
        totalHUF = items.stream().map(Item::getTotalHUFPrice).reduce(0, Integer::sum);
    }

    public Double getTotalEUR() {
        return totalEUR;
    }

    public void setTotalEUR() {
        totalEUR = items.stream().map(Item::getTotalEURPrice).reduce(0.0, Double::sum);
    }
}
