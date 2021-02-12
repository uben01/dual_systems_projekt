package com.dualsystems.invoices.data.entity;

import com.dualsystems.invoices.consuming.ExchangeClient;
import com.dualsystems.invoices.data.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity(name = "ITEM")
@Table(name = "ITEM")
public class Item extends AbstractEntity {

    @Column(name = "PRODUCTNAME", nullable = false)
    private String productName;

    @Column(name = "UNITPRICE", nullable = false)
    private int unitPrice;

    @Column(name = "QUANTITY", nullable = false)
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "invoice_id")
    @JsonIgnore
    Invoice invoice;

    @Transient
    private int totalHUFPrice;

    @Transient
    private int totalEURPrice;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTotalHUFPrice() {
        return quantity * unitPrice;
    }

    public double getTotalEURPrice() {
        return (quantity * unitPrice) / ExchangeClient.getExchangeRate();
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }
}
