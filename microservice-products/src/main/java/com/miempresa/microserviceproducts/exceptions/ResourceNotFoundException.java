package com.miempresa.microserviceproducts.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        // Llama al constructor de la superclase
        super(message);

    }
}

