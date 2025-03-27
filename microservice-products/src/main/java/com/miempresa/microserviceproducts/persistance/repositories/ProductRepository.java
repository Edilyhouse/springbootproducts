package com.miempresa.microserviceproducts.persistance.repositories;

import com.miempresa.microserviceproducts.persistance.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository  extends JpaRepository<ProductEntity, Integer> {

}
