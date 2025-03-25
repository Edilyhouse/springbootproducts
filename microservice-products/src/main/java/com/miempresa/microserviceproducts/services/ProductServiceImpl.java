package com.miempresa.microserviceproducts.services;

import com.miempresa.microserviceproducts.domain.Product;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ConditionalOnProperty(name="service.products", havingValue = "list", matchIfMissing = true)
@Service
public class ProductServiceImpl implements ProductService {

    List<Product> products = new ArrayList<>(Arrays.asList(
            new Product(1, "Laptop Dell XPS", 1500.59, 250 ),
            new Product(2, "Mouse Logotech spx", 148.99, 25 ),
            new Product(3, "Teclado Mecánico Corsair", 500.59, 1250 )
    ));


    @Override
    public List<Product> getAllProducts() {
        return products;
    }

    // Modifique el metodo para devolverver Optional<Product> para
    // Esto elimina la necesidad de manejar valores nulos manualmente en el controlador.

    @Override
    public Product getProductById(int id) {
        return products.stream()
                .filter(product -> product.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Product createProduct(Product product) {
        product.setId(products.size() + 1);
        products.add(product);
        return product;
    }

    @Override
    public Product updateProduct(int id, Product updateProduct) {
        return products.stream()
                .filter(product -> product.getId() == id)
                .findFirst()
                .map( product -> {
                    product.setName(updateProduct.getName());
                    product.setPrice(updateProduct.getPrice());
                    product.setStock(updateProduct.getStock());
                    return product;
                })
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    @Override
    public Product partiallyUpdateProduct(int id, Product updateProduct) {
        return products.stream()
                .filter(product -> product.getId() == id) // Filtra el producto por el ID
                .findFirst()
                .map(product -> {
                    // Actualizar solo los campos no nulos que llegaron en la solicitud
                    if (updateProduct.getName() != null) {
                        product.setName(updateProduct.getName());
                    }
                    if (updateProduct.getPrice() != null) {
                        product.setPrice(updateProduct.getPrice());
                    }
                    if (updateProduct.getStock() != null) {
                        product.setStock(updateProduct.getStock());
                    }
                    return product;
                })
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    /*
    @Override
    public Boolean deleteProduct(int id) {
        // Buscar y eliminar el producto en la lista
        boolean removed = products.removeIf(product -> product.getId() == id);
        if (removed) {
            return true;
        } else {

            return false;
        }
    }*/

    @Override
    public Boolean deleteProduct(int id) {
        return products.removeIf(product -> product.getId() == id);
    }


}

