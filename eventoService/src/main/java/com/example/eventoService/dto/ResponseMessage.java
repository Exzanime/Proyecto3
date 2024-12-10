package com.example.eventoService.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * Clase genérica para estructurar mensajes de respuesta.
 * @param <T> Tipo del cuerpo (puede ser nulo para errores).
 */
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class ResponseMessage<T> {
    private String message;
    private String cause;
    private int errorCode;
    private HttpStatus status;

    private LocalDateTime date;

    private T body;

    public ResponseMessage(String message, String cause, HttpStatus status, T body) {
        this.message = message;
        this.cause = cause;
        this.status = status;
        this.errorCode = status.value();
        this.date = LocalDateTime.now();
        this.body = body;
    }

    public ResponseMessage(String message, String cause, HttpStatus status) {
        this(message, cause, status, null);
    }
}
