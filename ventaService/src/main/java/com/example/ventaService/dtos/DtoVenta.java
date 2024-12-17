package com.example.ventaService.dtos;

import com.example.ventaService.model.Evento;
import com.example.ventaService.model.Usuario;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DtoVenta {

    //private Usuario usuarioClient;

//    private Evento eventoClient;

    private String userEmail;

    private String nombreEvento;
    private LocalDateTime fechaCompra;
    private BigDecimal precio;
}
