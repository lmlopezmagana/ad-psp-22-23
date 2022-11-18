package com.salesianostriana.dam.upload.exception;

import java.io.IOException;

public class StorageException extends RuntimeException {

    public StorageException(String message, Exception e) {
        super(message, e);
    }

    public StorageException(String message) {
        super(message);
    }
}
