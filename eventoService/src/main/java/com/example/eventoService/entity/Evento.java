package com.example.eventoService.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String nombre;

    @Column(nullable = false)
    String descripcion;

    @Column(nullable = false)
    String genero;

    @Column(nullable = false)
    String localidad;

    @Column(nullable = false)
    String recinto;

    @Column(nullable = false)
    LocalDateTime fecha;

    @Column(nullable = false)
    double precioMin;
    
    @Column(nullable = false)
    double precioMax;
}
