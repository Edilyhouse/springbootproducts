package com.miempresa.microserviceproducts.domain;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Modelo de DAtos que representa un producto de la tienda")

public class Product {

    @Schema
            (description = "Identificador único del producto",
                    example = "2",
                    accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;
    @Schema(description = "Nombre del Producto", example = "laptop dell xpx 15")
    private String name;
    @Schema(description = "Precio del Producto", example = "150.20")
    private Double price;
    @Schema(description = "Cantidad disponible en el inventario", example = "80")
    private Integer stock;
}

/*
*
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @RequiredArgsConstructor

    public class Product {
        @NonNull
        private Integer id;
        private String name;
        private Double price;
        @NonNull
        private Integer stock;
    }
*/