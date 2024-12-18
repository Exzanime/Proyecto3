package com.example.estadisticasBatchService.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DtoVenta {
    private String userEmail;
    private String nombreEvento;
    private LocalDateTime fechaCompra;
    private double precio;
}
