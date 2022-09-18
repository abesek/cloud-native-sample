package org.abesek.allocation.application;

import java.time.LocalDateTime;
import java.util.Optional;

import org.abesek.allocation.domain.model.OutOfStock;

public interface ProductService {
    void addBatch(String ref, String sku, int qty, Optional<LocalDateTime> eta);

    String allocate(String orderid, String sku, int qty) throws InvalidSku, OutOfStock;

}
