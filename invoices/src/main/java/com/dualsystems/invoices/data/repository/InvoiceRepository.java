package com.dualsystems.invoices.data.repository;

import com.dualsystems.invoices.data.entity.Invoice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

@Repository
@RestResource(exported = false)
public interface InvoiceRepository extends CrudRepository<Invoice, String> {
}
