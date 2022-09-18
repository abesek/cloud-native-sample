package org.abesek.allocation.domain.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;

@Table("order_lines")
@Data
public class OrderLine {
    private @Id int id;
    private String orderid;
    private String sku;
    private int qty;

    public OrderLine(String orderid, String sku, int qty) {
        this.orderid = orderid;
        this.sku = sku;
        this.qty = qty;
    }
}
