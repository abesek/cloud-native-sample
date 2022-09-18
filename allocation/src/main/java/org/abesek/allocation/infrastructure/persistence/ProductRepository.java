package org.abesek.allocation.infrastructure.persistence;

import org.abesek.allocation.domain.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, String> {
    
}
