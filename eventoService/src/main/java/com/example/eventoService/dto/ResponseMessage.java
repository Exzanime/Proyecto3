package com.example.eventoService.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor

public class ResponseMessage<T> {
    private String message;
    private String cause;
    private HttpStatus status;
    private int errorCode;
    private String date;

    private T body;

    public ResponseMessage(String message, String cause, HttpStatus status, T body) {
        this.message = message;
        this.cause = cause;
        this.status = status;
        this.body = body;
        this.errorCode = status.value();
    }
    public ResponseMessage(String message, String cause, HttpStatus status) {
        this.message = message;
        this.cause = cause;
        this.status = status;
        this.errorCode = status.value();
    }
    public ResponseMessage(String message, String cause, HttpStatus status, String date) {
        this.message = message;
        this.cause = cause;
        this.status = status;
        this.errorCode = errorCode;
        this.date = date;
    }
}
