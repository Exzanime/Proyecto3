package com.example.eventoService.dto;

import org.springframework.http.HttpStatus;

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }


    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;

    }
}
