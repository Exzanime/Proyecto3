package com.example.usuarioService.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

/**
 * Representa un usuario
 * <p>
 * Un objeto de la clase `Usuario` contiene todos los detalles sobre un usuario en internet,
 * como su nombre, apellido, email y fecha de nacimiento
 * </p>
 * @author Violeta, Denis, Alejandro, Nacho
 * @version 1.0
 * @date 2024
 * */
@Entity
@Data
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false)
    String nombre;
    @Column(nullable = false)
    String apellido;
    @Column(nullable = false)
    String email;
    @Column(nullable = false)
    LocalDate fechaNacimiento;

}
