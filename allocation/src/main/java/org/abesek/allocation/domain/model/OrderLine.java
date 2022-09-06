package org.abesek.allocation.domain.model;

import lombok.Data;

@Data
public class OrderLine {
    private String orderid;
    private String sku;
    private int qty;
}
