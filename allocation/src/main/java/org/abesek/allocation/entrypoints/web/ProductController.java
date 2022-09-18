package org.abesek.allocation.entrypoints.web;

import org.abesek.allocation.application.InvalidSku;
import org.abesek.allocation.application.ProductService;
import org.abesek.allocation.domain.model.Batch;
import org.abesek.allocation.domain.model.OrderLine;
import org.abesek.allocation.domain.model.OutOfStock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/batches")
    @ResponseStatus(code = HttpStatus.OK, reason = "OK")
    String addBatch(@RequestBody Batch batch) {
        productService.addBatch(batch.getReference(), batch.getSku(), batch.getPurchasedQuantity(), batch.getEta());
        return "200, OK";
    }

    @PostMapping("/allocate")
    String allocate(@RequestBody OrderLine line) {
        try {
            return productService.allocate(line.getOrderid(), line.getSku(), line.getQty());
        } catch (OutOfStock | InvalidSku e) {
            return e.getMessage();
        }        
    }

}
