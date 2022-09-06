package org.abesek.allocation.domain.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString(includeFieldNames=true)
public class Batch implements Comparable<Batch> {
    @Getter @Setter private String reference;
    @Getter @Setter private String sku;
    @Getter @Setter private Optional<LocalDateTime> eta;
    @Getter @Setter private int purchasedQuantity;

    private Set<OrderLine> allocations = new HashSet<>();

    public Batch(String ref, String sku, int qty, Optional<LocalDateTime> eta) {
        this.reference = ref;
        this.sku = sku;
        this.eta = eta;
        this.purchasedQuantity = qty;
    }

    int getAllocatedQuantity() {
        return this.allocations.stream()
                .map(line -> line.getQty())
                .mapToInt(Integer::intValue)
                .sum();
    }

    int getAvailableQuantity() {
        return this.purchasedQuantity - this.getAllocatedQuantity();
    }

    public boolean canAllocate(OrderLine line) {
        return this.sku == line.getSku() && this.getAvailableQuantity() >= line.getQty();
    }

    public void allocate(OrderLine line) {
        if (this.canAllocate(line)) {
            this.allocations.add(line);
        }
    }

    public void deallocate(OrderLine line) {
        if (this.allocations.contains(line)) {
            this.allocations.remove(line);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Batch)) {
            return false;
        }
        Batch other = (Batch) o;
        return this.reference == other.reference;
    }

    @Override
    public int compareTo(Batch o) {
        if (this.eta.isEmpty()) {
            return -1;
        }
        if (o.eta.isEmpty()) {
            return 1;
        }
        return this.eta.get().compareTo(o.eta.get());
    }

}