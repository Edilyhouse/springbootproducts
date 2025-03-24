package com.miempresa.microserviceproducts.controller;

import com.miempresa.microserviceproducts.domain.Product;
import com.miempresa.microserviceproducts.services.ProductService;
import com.miempresa.microserviceproducts.services.ProductServiceImpl;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/productos")
public class ProductController {

    // definir objeto para conectar la poho con los enpoints
    // polimorfismo

    ProductService productService = new ProductServiceImpl();

    // Endpoint obtener todos los productos
    @GetMapping()
    public ResponseEntity<List<Product>>  getAllProducts(){
       List<Product> products = productService.getAllProducts();
       return products.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(products);
    }

    // Endpoint obtener un Producto por ID
    @GetMapping("/{id}")
    // *********  es mejor hacerlo con Stream  ********

    public ResponseEntity<?> getProductById(@PathVariable int id) {
        Product product = productService.getProductById(id);
        return product != null ? ResponseEntity.ok(product) : ResponseEntity.notFound().build();
    }


    // otra forma
    /* public Product getProductById(int id){
        for(Product product: products){
            if(product.getId() == id){
                return product;
            }
        }
        return null;
    }*/

    // crear nuevo recurso
    @PostMapping()
    public ResponseEntity<Product>  createProduct(@RequestBody Product product, UriComponentsBuilder uriComponentsBuilder){
          Product newProduct = productService.createProduct(product);
          // bajamos el que viene como parametro
          // path("/productos/{id}") construye el path o url
          URI location = uriComponentsBuilder.path("/productos/{id}").buildAndExpand(newProduct.getId()).toUri();
          return ResponseEntity.created(location).body(newProduct);
    }

    // Actualizar completamente un producto(PUT)
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable  int id, @RequestBody Product updateProduct){
        Product updatedProduct = productService.updateProduct(id, updateProduct);
        return updatedProduct != null ? ResponseEntity.ok(updatedProduct) : ResponseEntity.notFound().build();
    }

    // Actualizar Parcialemente
    @PatchMapping("/{id}")
    public ResponseEntity<Product>  partiallyUpdateProduct(@PathVariable int id, @RequestBody Product updateProduct) {
        Product updatedProduct = productService.partiallyUpdateProduct(id, updateProduct);
        return updatedProduct != null ? ResponseEntity.ok(updatedProduct) : ResponseEntity.notFound().build();
    }

    // borrar un producto
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id) {
        return productService.deleteProduct(id)
                ? ResponseEntity.ok("Producto Eliminado")
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado");  // Si no se encontró el producto
    }


}
