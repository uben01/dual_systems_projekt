package com.dualsystems.invoices.service;

import com.dualsystems.invoices.data.AbstractEntity;
import com.dualsystems.invoices.data.entity.Invoice;
import com.dualsystems.invoices.data.repository.InvoiceRepository;
import com.dualsystems.invoices.data.repository.ItemRepository;
import com.dualsystems.invoices.exception.DueDateBeforeIssueDateException;
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

    public void createInvoice(Invoice invoice) throws DueDateBeforeIssueDateException {
        // Input checks
        if(invoice.getDueDate().before(invoice.getIssueDate())){
            throw new DueDateBeforeIssueDateException();
        }

        Invoice persistedInvoice = invoiceRepository.save(invoice);
        invoice.getItems().forEach(item -> {
            item.setId(AbstractEntity.generateUniqueId());
            item.setInvoice(persistedInvoice);
            itemRepository.save(item);
        });
    }
}
