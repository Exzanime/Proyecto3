package com.example.ventaService.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DtoTarjeta {
    private String nombreTitular;
    private String numero;
    private String mesCaducidad;
    private String yearCaducidad;
    private String cvv;
}
