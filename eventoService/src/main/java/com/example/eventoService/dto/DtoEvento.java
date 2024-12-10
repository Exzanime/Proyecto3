package com.example.eventoService.dto;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DtoEvento {

    String nombre;

    String descripcion;

    String genero;

    String localidad;

    String recinto;

    LocalDateTime fecha;

    double precioMin;

    double precioMax;
}
