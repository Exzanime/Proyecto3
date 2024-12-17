package com.example.ventaService.dtos;

import lombok.Data;

@Data
public class VentaRequest {
    private String nombreTitular;
    private String numero;
    private String mesCaducidad;
    private String yearCaducidad;
    private String cvv;
    private Long usuarioId;
    private Long eventoId;
    private String userEmail;
    private String nombreEvento;
    private double precio;
    private String fechaCompra;
}
