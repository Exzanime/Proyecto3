package com.example.ventaService.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VentaRequest {
    private String nombreTitular;
    private String numero;
    private String mesCaducidad;
    private String yearCaducidad;
    private String cvv;
    private Long usuarioId;
    private Long eventoId;
    private String userEmail;
    private double precio;
}
