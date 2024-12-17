package com.example.ventaService.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DtoTarjeta {
    private String nombre;
    private String numero;
    private String fechaExpiracion;
    private String cvv;
}
