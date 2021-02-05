package com.dualsystems.invoices.web;

import com.dualsystems.invoices.consuming.ExchangeClient;
import com.dualsystems.invoices.data.entity.Invoice;
import com.dualsystems.invoices.service.InvoiceService;
import com.dualsystems.invoices.service.ItemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = "/invoices", produces = "application/json")
public class InvoiceWebController {
    private final InvoiceService invoiceService;
    private final ItemService itemService;

    @Autowired
    public InvoiceWebController(InvoiceService invoiceService, ItemService itemService) {
        this.invoiceService = invoiceService;
        this.itemService = itemService;
    }

    @GetMapping(produces = "application/json")
    public List<Invoice> getInvoices() {
        return invoiceService.getInvoices();
    }

    @GetMapping(value = "/exchangeRate", produces = "application/json")
    public String getExchangeRate() {
        return "{\"rate\": " + ExchangeClient.getExchangeRate() + "}";
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @Transactional
    public String createInvoiceWithItems(@RequestBody String JSONObject) {
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            HashMap<String, Object> objectMap = objectMapper.readValue(JSONObject, HashMap.class);
            ArrayList<HashMap<String,String>> items = (ArrayList<HashMap<String,String>>) objectMap.remove("items");

            Invoice invoice = invoiceService.createInvoice(objectMap);
            for(HashMap<String,String> map : items){
                itemService.createItem(invoice, map);
            }

            return "{\"result\": \"success\"}";
        } catch (Exception e){
            e.printStackTrace();
            return "{\"result\": \"fail\"}";
        }
    }
}
