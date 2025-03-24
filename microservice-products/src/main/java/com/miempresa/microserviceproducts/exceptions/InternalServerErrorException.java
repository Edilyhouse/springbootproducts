package com.miempresa.microserviceproducts.exceptions;


public class InternalServerErrorException extends RuntimeException{
    public InternalServerErrorException(String message){
        super(message);
    }
}
