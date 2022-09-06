package org.abesek.allocation.domain.model;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

public class Product {
    private String sku;
    private List<Batch> batches;
    private int versionNumber;

    public Product(String sku, List<Batch> batches) {
        this.sku = sku;
        this.batches = batches;
        this.versionNumber = 0;
    }

    public Product(String sku, List<Batch> batches, int versionNumber) {
        this.sku = sku;
        this.batches = batches;
        this.versionNumber = versionNumber;
    }

    public String allocate(OrderLine line) throws OutOfStock {
        Optional<Batch> batch = this.batches.stream()
                .filter(Objects::nonNull)
                .sorted((x, y) -> x.compareTo(y))
                .filter(b -> b.canAllocate(line))
                .findFirst();
        try {
            batch.get().allocate(line);
            this.versionNumber += 1;
            return batch.get().getReference();
        } catch (NoSuchElementException e) {
            throw new OutOfStock(String.format("Out of stock for sku %s", line.getSku()));
        }
    }
    
}
