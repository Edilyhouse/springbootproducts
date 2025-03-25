package com.miempresa.microserviceproducts.controller;

import com.miempresa.microserviceproducts.domain.Product;
import com.miempresa.microserviceproducts.exceptions.ResourceNotFoundException;
import com.miempresa.microserviceproducts.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v2/productos")
public class ProductControllerV2 {
    private final ProductService productService;

    @Autowired
    public ProductControllerV2(@Lazy ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public ResponseEntity<List<Product>> getAllProducts(){
        List<Product> products = productService.getAllProducts();
        return products.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(products);
    }


    @GetMapping("/{id}")


    public ResponseEntity<?> getProductById(@PathVariable String id) {
        int productId = Integer.parseInt(id);
        Product product = productService.getProductById(productId);
        if(product == null){
            throw new ResourceNotFoundException("Producto con Id " + id + "No encontrado");
        }
        return ResponseEntity.ok(product);
    }

    @PostMapping()
    public ResponseEntity<Product>  createProduct(@RequestBody Product product, UriComponentsBuilder uriComponentsBuilder){
        Product newProduct = productService.createProduct(product);
        // bajamos el que viene como parametro
        // path("/productos/{id}") construye el path o url
        URI location = uriComponentsBuilder.path("/api/v1/productos/{id}").buildAndExpand(newProduct.getId()).toUri();
        return ResponseEntity.created(location).body(newProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable  int id, @RequestBody Product updateProduct){
        Product updatedProduct = productService.updateProduct(id, updateProduct);
        if(updatedProduct == null){
            throw new ResourceNotFoundException("Producto con ID " + id + "No encontrado");
        }
        return ResponseEntity.ok(updatedProduct);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Product>  partiallyUpdateProduct(@PathVariable int id, @RequestBody Product updateProduct) {
        Product updatedProduct = productService.partiallyUpdateProduct(id, updateProduct);
        if(updatedProduct == null){
            throw new ResourceNotFoundException("Producto con ID " + id + "No encontrado");
        }
        return  ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id) {
        return productService.deleteProduct(id)
                ? ResponseEntity.ok("Producto Eliminado")
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado");  // Si no se encontró el producto
    }
}
