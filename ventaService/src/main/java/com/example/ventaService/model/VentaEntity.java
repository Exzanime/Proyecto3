package com.example.ventaService.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VentaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String userEmail;

    private String nombreEvento;


//    @Transient
//    private Usuario usuarioClient;


//    @Transient
//    private Evento eventoClient;

    private LocalDateTime fechaCompra;
    private BigDecimal precio;
}
