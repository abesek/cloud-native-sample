package org.abesek.allocation.domain.model;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("products")
public class Product {
    private @Id String sku;
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
            versionNumber = versionNumber + 1;
            return batch.get().getReference();
        } catch (NoSuchElementException e) {
            throw new OutOfStock(String.format("Out of stock for sku %s", line.getSku()));
        }
    }

    public void addBatch(Batch batch) {
        if (batch != null) {
            this.batches.add(batch);
        }
    }
    
}
