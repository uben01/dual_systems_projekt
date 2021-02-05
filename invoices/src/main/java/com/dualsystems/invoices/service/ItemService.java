package com.dualsystems.invoices.service;

import com.dualsystems.invoices.data.entity.Invoice;
import com.dualsystems.invoices.data.entity.Item;
import com.dualsystems.invoices.data.repository.InvoiceRepository;
import com.dualsystems.invoices.data.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final InvoiceRepository invoiceRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository, InvoiceRepository invoiceRepository) {
        this.itemRepository = itemRepository;
        this.invoiceRepository = invoiceRepository;
    }

    public void createItem(Invoice invoice, HashMap<String, String> objectMap) {
        Item item = new Item();

        item.setInvoice(invoice);
        item.setProductName(objectMap.get("productName"));
        item.setQuantity(Integer.parseUnsignedInt(objectMap.get("quantity")));
        item.setUnitPrice(Integer.parseUnsignedInt(objectMap.get("unitPrice")));

        itemRepository.save(item);
    }
}
