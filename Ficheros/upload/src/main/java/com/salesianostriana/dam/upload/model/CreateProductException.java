package com.salesianostriana.dam.upload.model;


public class CreateProductException extends RuntimeException {
    public CreateProductException(String msg, Exception ex) {
        super(msg, ex);
    }
}
