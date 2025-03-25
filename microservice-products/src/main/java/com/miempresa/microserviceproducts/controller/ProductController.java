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
@RequestMapping("/productos")
public class ProductController {

    //  Instancia de clase definir objeto para conectar la poho con los enpoints
    //  Polimorfismo
    //  ProductService productService = new ProductServiceImpl();

    /* Inyección de dependencias puede
        *
        * POR CAMPO  no es muy recomendada no se puede mockear
        *      @Autowired
               private ProductService productService;
        POR METODO setter
        *   siempre hay que tener una instancia
        *   private ProductService productService;
        *   @Autowired
            public void setProductService(ProductService productService) {
                this.productService = productService;
            } La anotacion va sobre el metodo setter Autowired SOLAMENTE VA EN EL CAMPO EN EL QUE SE VA A INYECTAR
        *
        Y POR CONSTRUCTOR  Es la mejor practiva
    * */

    private final ProductService productService;

    // Inyeccion de dependencia
    @Autowired
    public ProductController(@Lazy ProductService productService) {
        this.productService = productService;
    }

    /* si fueran varios servicios o inyecciones de dependencias
    *   public ProductController(ServicioA servicioA, @Lazy ServicioB servicioB)
    *
    * */
    // Endpoint obtener todos los productos

    @GetMapping()
    public ResponseEntity<List<Product>>  getAllProducts(){
       List<Product> products = productService.getAllProducts();
       return products.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(products);
    }

    // Endpoint obtener un Producto por ID
    @GetMapping("/{id}")
    // *********  es mejor hacerlo con Stream  ********

    public ResponseEntity<?> getProductById(@PathVariable String id) {
        int productId = Integer.parseInt(id);
        Product product = productService.getProductById(productId);
        if(product == null){
            throw new ResourceNotFoundException("Producto con Id " + id + "No encontrado");
        }
        return ResponseEntity.ok(product);
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
        if(updatedProduct == null){
            throw new ResourceNotFoundException("Producto con ID " + id + "No encontrado");
        }
        return ResponseEntity.ok(updatedProduct);
    }

    // Actualizar Parcialemente
    @PatchMapping("/{id}")
    public ResponseEntity<Product>  partiallyUpdateProduct(@PathVariable int id, @RequestBody Product updateProduct) {
        Product updatedProduct = productService.partiallyUpdateProduct(id, updateProduct);
        if(updatedProduct == null){
            throw new ResourceNotFoundException("Producto con ID " + id + "No encontrado");
        }
        return  ResponseEntity.ok(updatedProduct);
    }

    // borrar un producto
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id) {
        return productService.deleteProduct(id)
                ? ResponseEntity.ok("Producto Eliminado")
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado");  // Si no se encontró el producto
    }


}
