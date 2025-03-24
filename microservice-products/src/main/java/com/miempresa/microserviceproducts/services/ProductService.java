package com.miempresa.microserviceproducts.services;

import com.miempresa.microserviceproducts.domain.Product;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

/**
 * Interfaz que define los métodos para gestionar productos en el sistema.
 */

public interface ProductService {

    /**
     * Obtiene todos los productos disponibles.
     * @return Lista de productos.
     */

    List<Product> getAllProducts();

    /**
     * Obtiene un producto por su ID.
     * @param id Identificador del producto.
     * @return Producto encontrado.
     */

    Product getProductById(int id);


    /**
     * Crea un nuevo producto.
     * @param product Datos del nuevo producto.
     * @return Producto creado.
     */

    Product createProduct(Product product);

    /**
     * Actualiza un producto existente por su ID.
     * @param id Identificador del producto.
     * @param updateProduct Datos actualizados del producto.
     * @return Producto actualizado.
     */

    Product updateProduct(int id,Product updateProduct);

    /**
     * Realiza una actualización parcial de un producto.
     * @param id Identificador del producto.
     * @param updateProduct Datos a actualizar en el producto.
     * @return Producto actualizado parcialmente.
     */

    Product partiallyUpdateProduct(int id, Product updateProduct);

    /**
     * Elimina un producto por su ID.
     * @param id Identificador del producto a eliminar.
     * @return Mensaje de confirmación.
     */
    Boolean deleteProduct(int id);
}
