package com.miempresa.microserviceproducts.domain;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Product {

    private Integer id;
    private String name;
    private Double price;
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