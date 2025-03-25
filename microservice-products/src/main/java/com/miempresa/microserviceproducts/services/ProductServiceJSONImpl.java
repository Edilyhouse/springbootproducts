package com.miempresa.microserviceproducts.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.miempresa.microserviceproducts.domain.Product;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

// TODO probar el @primary ya que no funciono
public class ProductServiceJSONImpl implements ProductService {

    @Override
    public List<Product> getAllProducts() {
        try{
            return new ObjectMapper().readValue(
                    this.getClass().getResourceAsStream("/products.json"),
                    new TypeReference<List<Product>>() {
                    }
            );
        } catch (Exception e){
            throw new RuntimeException("Error al Leer el archivo", e);
        }
    }

    @Override
    public Product getProductById(int id) {
        return null;
    }

    @Override
    public Product createProduct(Product product) {
        return null;
    }

    @Override
    public Product updateProduct(int id, Product updateProduct) {
        return null;
    }

    @Override
    public Product partiallyUpdateProduct(int id, Product updateProduct) {
        return null;
    }

    @Override
    public Boolean deleteProduct(int id) {
        return null;
    }
}
