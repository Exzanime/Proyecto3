package com.example.eventoService.controller.error;

import org.springframework.http.HttpStatus;

public class EventoDuplicadoExcp extends RuntimeException {

    private int codigo;
    private String causaPersonalizada;

    /**
     * Constructs a new runtime exception with the specified detail message, a custom cause and HTTP status.
     *
     * @param message           the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     * @param causaPersonalizada the custom cause message (which is saved for later retrieval by the {@link #getCausaPersonalizada()} method).
     * @param status            the HTTP status (used to set the error code).
     */
    public EventoDuplicadoExcp(String message, String causaPersonalizada, HttpStatus status) {
        super(message);
        this.codigo = status.value();
        this.causaPersonalizada = causaPersonalizada;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getCausaPersonalizada() {
        return causaPersonalizada;
    }

    @Override
    public Throwable getCause() {
        return new IllegalStateException(causaPersonalizada);
    }
}
