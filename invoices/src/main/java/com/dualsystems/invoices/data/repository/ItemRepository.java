package com.dualsystems.invoices.data.repository;

import com.dualsystems.invoices.data.entity.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

@Repository
@RestResource(exported = false)
public interface ItemRepository extends CrudRepository<Item, String> {
}
