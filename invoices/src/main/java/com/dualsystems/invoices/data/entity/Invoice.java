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

    @Column(name = "ISSUEDATE", nullable = false)
    private Date issueDate;

    @Column(name = "DUEDATE", nullable = false)
    private Date dueDate;

    @Column(name = "COMMENT")
    private String comment;

    @OneToMany(mappedBy = "invoice",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Item> items = new ArrayList<>();

    @Transient
    private int totalHUF;

    @Transient
    private int totalEUR;

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

    public int getTotalHUF() {
        int total = 0;
        for (Item tempItem : items) {
            total += tempItem.getTotalHUFPrice();
        }

        return total;
    }

    public double getTotalEUR() {
        double total = 0;
        for (Item tempItem : items) {
            total += tempItem.getTotalEURPrice();
        }

        return total;
    }
}
