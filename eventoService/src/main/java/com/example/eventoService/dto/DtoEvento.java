package com.example.eventoService.dto;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class DtoEvento {

    String nombre;

    String descripcion;

    String genero;

    String localidad;

    String recinto;

    String fecha;

    double precioMin;

    double precioMax;
}
