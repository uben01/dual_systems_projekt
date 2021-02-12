package com.dualsystems.invoices.service;

import com.dualsystems.invoices.data.entity.Invoice;
import com.dualsystems.invoices.data.repository.InvoiceRepository;
import com.dualsystems.invoices.data.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final ItemRepository itemRepository;

    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository, ItemRepository itemRepository) {
        this.invoiceRepository = invoiceRepository;
        this.itemRepository = itemRepository;
    }

    public List<Invoice> getInvoices() {
        Iterable<Invoice> invoices = invoiceRepository.findAll();
        List<Invoice> invoiceList = new ArrayList<>();
        invoices.forEach(invoiceList::add);

        return invoiceList;
    }

    public Invoice createInvoice(HashMap<String, Object> objectMap) {
        Invoice invoice = new Invoice();
        invoice.setCustomerName((String)objectMap.get("customerName"));
        invoice.setIssueDate(Date.valueOf((String)objectMap.get("issueDate")));
        invoice.setDueDate(Date.valueOf((String)objectMap.get("dueDate")));
        invoice.setComment((String)objectMap.get("comment"));

        invoiceRepository.save(invoice);
        return invoice;
    }
}
