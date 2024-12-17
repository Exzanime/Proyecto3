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
    String nombre;
    String numero;
    String fechaCaducidad;
    String cvv;
}
