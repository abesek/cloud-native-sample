package org.abesek.allocation.application;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import org.abesek.allocation.domain.model.Batch;
import org.abesek.allocation.domain.model.OrderLine;
import org.abesek.allocation.domain.model.OutOfStock;
import org.abesek.allocation.domain.model.Product;
import org.abesek.allocation.infrastructure.persistence.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository repo;

    @Override
    public void addBatch(String ref, String sku, int qty, Optional<LocalDateTime> eta) {
        Optional<Product> product = repo.findById(sku);
        if (product.isEmpty()) {
            repo.save(new Product(sku, new ArrayList<Batch>()));
        }
        product.get().addBatch(new Batch(ref, sku, qty, eta));        
    }

    @Override
    public String allocate(String orderid, String sku, int qty) throws InvalidSku, OutOfStock {
        OrderLine line = new OrderLine(orderid, sku, qty);
        Optional<Product> product = repo.findById(sku);
        if (product.isEmpty()) {
            throw new InvalidSku(String.format("Invalid Sku %s", line.getSku()));
        }
        return product.get().allocate(line);
    }
    
}
