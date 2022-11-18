package com.salesianostriana.dam.errores;

import com.salesianostriana.dam.errores.excepciones.EntityNotFoundException;
import com.salesianostriana.dam.errores.modelo.ApiError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalRestControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<?> handleNotFoundException(EntityNotFoundException ex, WebRequest request) {
        return buildApiError(HttpStatus.NOT_FOUND, ex, request);
    }

    /*
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return buildApiError(ex, request);
    }

    */


    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        //return super.handleExceptionInternal(ex, body, headers, status, request);

        ApiError error = ApiError.builder()
                .estado(status)
                .codigo(status.value())
                .ruta(((ServletWebRequest)request).getRequest().getRequestURI())
                .mensaje(ex.getMessage())
                .build();

        return ResponseEntity.status(status).body(error);

    }

    private final ResponseEntity<Object> buildApiError(HttpStatus status, Exception ex, WebRequest request) {
        return ResponseEntity
                .status(status)
                .body(new ApiError(status, ex.getMessage(), ((ServletWebRequest) request).getRequest().getRequestURI()));

    }

}
