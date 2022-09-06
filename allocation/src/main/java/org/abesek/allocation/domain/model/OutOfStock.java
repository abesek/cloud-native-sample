package org.abesek.allocation.domain.model;

public class OutOfStock extends Exception {
    public OutOfStock(String msg) {
        super(msg);
    }
}
