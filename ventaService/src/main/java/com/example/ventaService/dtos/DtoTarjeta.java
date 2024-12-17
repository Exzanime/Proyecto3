package com.example.ventaService.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DtoTarjeta {
    private String nombreTitular;
    private String numero;
    private String mesCaducidad;
    private String yearCaducidad;
    private String cvv;
}
