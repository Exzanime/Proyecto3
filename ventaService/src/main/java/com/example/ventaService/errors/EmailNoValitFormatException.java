package com.example.ventaService.errors;

public class EmailNoValitFormatException extends RuntimeException {
    public EmailNoValitFormatException() {
        super("El email no tiene un formato valido." +
                "Ejemplo de formato valido:" +
                " ejemplo @ ejemplo . com");
    }
}
