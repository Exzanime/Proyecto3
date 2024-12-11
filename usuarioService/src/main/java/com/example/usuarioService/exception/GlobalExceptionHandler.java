package com.example.usuarioService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Controlador global para manejar excepciones
 * @author denis, violeta, nacho, alejandro
 * @since 2024-12-10
 * @version 1.0
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Retorna una respuesta HTTP con estado {@code 409 Conflict} y un cuerpo que incluye detalles del error y un mensaje descriptivo.
     * @param ex El mensaje de la excepcion
     * @return Respuesta HTTP con estado {@code 409 Conflict} y un cuerpo en formato JSON que
     *         contiene el tipo de error y el mensaje asociado.
     */
    @ExceptionHandler(DuplicadoException.class)
    public ResponseEntity<?> handleDuplicadoException(DuplicadoException ex){
        Map<String,String> response = new HashMap<>();
        response.put("error","Conflict");
        response.put("message",ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }
}
