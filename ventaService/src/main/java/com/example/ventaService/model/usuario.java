package com.example.ventaService.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class usuario {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate fechaNacimiento;

}
