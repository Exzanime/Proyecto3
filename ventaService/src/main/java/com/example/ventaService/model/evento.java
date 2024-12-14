package com.example.ventaService.model;

import lombok.Data;

@Data
public class evento {

    Long id;
    String nombre;

    String descripcion;

    String genero;

    String localidad;

    String recinto;

    String fecha;

    double precioMin;

    double precioMax;

}
