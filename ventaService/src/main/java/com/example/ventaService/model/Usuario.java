package com.example.ventaService.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

//@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    private static final long serialVersionUID = 1L;
    //@Id
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate fechaNacimiento;
}
