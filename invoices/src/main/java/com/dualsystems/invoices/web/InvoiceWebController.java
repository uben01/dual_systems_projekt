package com.dualsystems.invoices.web;

import com.dualsystems.invoices.consuming.ExchangeClient;
import com.dualsystems.invoices.data.entity.Invoice;
import com.dualsystems.invoices.exception.DueDateBeforeIssueDateException;
import com.dualsystems.invoices.service.InvoiceService;
import com.dualsystems.invoices.service.ItemService;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = "/invoices", produces = "application/json")
public class InvoiceWebController {
    private final InvoiceService invoiceService;
    private final ItemService itemService;
    private final JsonFactory factory;

    @Autowired
    public InvoiceWebController(InvoiceService invoiceService, ItemService itemService) {
        this.invoiceService = invoiceService;
        this.itemService = itemService;

         this.factory = new ObjectMapper().getFactory();
    }

    @GetMapping(produces = "application/json")
    public List<Invoice> getInvoices() {
        return invoiceService.getInvoices();
    }

    @GetMapping(value = "/exchangeRate", produces = "application/json")
    public String getExchangeRate() throws IOException {
        return createJSONMessage("rate", String.valueOf(ExchangeClient.getExchangeRate()));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = "application/json", produces = "application/json")
    @Transactional
    public String createInvoiceWithItems(@RequestBody Invoice invoice) throws IOException, DueDateBeforeIssueDateException {
        invoiceService.createInvoice(invoice);

        return createJSONMessage("result", "success");
    }

    // I'm sure that, there should be an easier way! ^^
    private String createJSONMessage(String field, String value) throws IOException{
        StringWriter writer = new StringWriter();
        JsonGenerator generator = factory.createGenerator(writer);

        generator.writeStartObject();
        generator.writeStringField(field, value);
        generator.writeEndObject();
        generator.close();

        return writer.toString();
    }
}
