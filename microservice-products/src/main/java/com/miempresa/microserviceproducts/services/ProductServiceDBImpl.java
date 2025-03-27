package com.miempresa.microserviceproducts.services;

import com.miempresa.microserviceproducts.domain.Product;
import com.miempresa.microserviceproducts.exceptions.ResourceNotFoundException;
import com.miempresa.microserviceproducts.persistance.entities.ProductEntity;
import com.miempresa.microserviceproducts.persistance.repositories.ProductRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import java.util.Optional;



import java.util.List;

@ConditionalOnProperty(name = "service.products", havingValue = "dbservice")
@Service

public class ProductServiceDBImpl implements ProductService{

    private final ProductRepository productRepository;

    public ProductServiceDBImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /* vieja escuela GET ALL PRODUCTS FROM DATA BASE
    @Override
    public List<Product> getAllProducts() {
        List<ProductEntity> entities = productRepository.findAll();
        List<Product> products = new ArrayList<>();
        for(ProductEntity entity : entities){
            products.add(convertToDomain(entity));
        }
        return products
    }*/


    // GET ALL PRODUCTS FROM DATA BASE programación functional programming
    // findAll nunca retornará null ya que retorna una lista vacia si no hay nada en la dba
    @Override
    public List<Product> getAllProducts(){
        return productRepository.findAll().stream()
                .map(this::convertToDomain)
                .toList();
                // .collect(Collectors.toList()); prior Java 16
    }

    /* vieja escuela
    *  @Override
       public Product getProductById(int id) {
           ProductEntity entity = productRepository.findById(id).orElse(null);
           return (entity != null) ? convertToDomain(entity) : null;
         }
    * */

    // Programación funcional
    @Override
    public Product getProductById(int id) {
        return productRepository.findById(id)
                .map(this::convertToDomain)
                .orElseThrow(() ->new ResourceNotFoundException("No encontrado: "));
    }

    /* Vieja escuela crear productos
    @Override
    public Product createProduct(Product product) {
        ProductEntity entity = convertToEntity(product);
        ProductEntity savedEntity = productRepository.save(entity);
        return convertToDomain(savedEntity);
    }
    */

    /* VALIDACIONES PROGRAMACION FUNCIONAL
    @Override
    public Product createProduct(Product product) {
        return Optional.ofNullable(product)
                .filter(p -> p.getName() != null && !p.getName().isEmpty())
                .filter(p -> p.getPrice() != null && p.getPrice() > 0)
                .map(this::convertToEntity)
                .map(productRepository::save)
                .map(this::convertToDomain)
                .orElseThrow(() -> new InvalidProductException("Invalid product data"));
    }
    */

    // Functional programming CREATE / INSERT  A NEW PRODUCT INTO DATABASE
    @Override
    public Product createProduct(Product product) {
        return Optional.ofNullable(product)
                .map(this::convertToEntity)
                .map(productRepository::save)
                .map(this::convertToDomain)
                .orElseThrow(() -> new IllegalArgumentException("Product cannot be null"));
    }

    //  UPDATE  PRODUCT TO DATABASE

    /*  VIEJA ESCULA
    @Override
    public Product updateProduct(int id, Product updateProduct) {
       if(productRepository.existsById(id)){
           ProductEntity entity = convertToEntity(updateProduct);
           entity.setId(id);
           ProductEntity updateEntity = productRepository.save(entity);
           return convertToDomain(updateEntity);
       }
       return null;
    }
    */
    public Product updateProduct(int id, Product updateProduct){
        return productRepository.findById(id)
                .map(existingEntity -> {
                    ProductEntity updatedEntity = convertToEntity(updateProduct);
                    updatedEntity.setId(id);
                    return productRepository.save(updatedEntity);
                })
                .map(this::convertToDomain)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id" + id + "not found"));
    }

    /* utilizando Optional
     @Override
        public Product updateProduct(int id, Product updateProduct) {
            return Optional.ofNullable(updateProduct)
                    .flatMap(product -> productRepository.findById(id)
                    .map(existingEntity -> {
                        ProductEntity updatedEntity = convertToEntity(updateProduct);
                        updatedEntity.setId(id);
                        return updatedEntity;
                    })
                    .map(productRepository::save)
                    .map(this::convertToDomain)
                    .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found or update data is invalid"));
        }*/


    /*                       PARTIAL UPDATING
            @Override
            public Product partiallyUpdateProduct(int id, Product updateProduct) {
                ProductEntity existingEntity = productRepository.findById(id).orElse(null);
                if(existingEntity != null){
                    if(updateProduct.getName() != null){
                        existingEntity.setName(updateProduct.getName());
                    }
                    if(updateProduct.getPrice() != null){
                        existingEntity.setPrice(updateProduct.getPrice());
                    }
                    if(updateProduct.getStock() != null){
                        existingEntity.setStock(updateProduct.getStock());
                    }

                    ProductEntity updateEntity = productRepository.save(existingEntity);
                    return convertToDomain(updateEntity)
                }
                return null;
            }

            //  separando funcionalidad    PROGRAMACION FUNCIONAL
            @Override
            public Product partiallyUpdateProduct(int id, Product updateProduct) {
                return productRepository.findById(id)
                        .map(existingEntity -> {
                            updateEntityFields(existingEntity, updateProduct);
                            return productRepository.save(existingEntity);
                        })
                        .map(this::convertToDomain)
                        .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));
            }

            private void updateEntityFields(ProductEntity existingEntity, Product updateProduct) {
                Optional.ofNullable(updateProduct.getName()).ifPresent(existingEntity::setName);
                Optional.ofNullable(updateProduct.getPrice()).ifPresent(existingEntity::setPrice);
                Optional.ofNullable(updateProduct.getStock()).ifPresent(existingEntity::setStock);
            }
     */

    @Override
    public Product partiallyUpdateProduct(int id, Product updateProduct) {
        return productRepository.findById(id)
                .map(existingEntity -> {
                    Optional.ofNullable(updateProduct.getName()).ifPresent(existingEntity::setName);
                    Optional.ofNullable(updateProduct.getPrice()).ifPresent(existingEntity::setPrice);
                    Optional.ofNullable(updateProduct.getStock()).ifPresent(existingEntity::setStock);
                    return productRepository.save(existingEntity);
                })
                .map(this::convertToDomain)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }


    @Override
    public Boolean deleteProduct(int id) {
        if(productRepository.existsById(id)){
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Métodos para convertir entre ProductEntity y Product
    // pueden ser privados solo aquí los vamos utilizar

    private Product convertToDomain(ProductEntity entity) {
        return new Product(entity.getId(), entity.getName(), entity.getPrice(), entity.getStock());
    }

    private ProductEntity convertToEntity(Product product){
        ProductEntity entity = new ProductEntity();
        entity.setName(product.getName());
        entity.setPrice(product.getPrice());
        entity.setStock(product.getStock());
        return entity;
    }

}
