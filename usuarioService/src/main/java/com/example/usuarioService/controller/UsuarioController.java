package com.example.usuarioService.controller;

import com.example.usuarioService.dto.DtoUsuario;
import com.example.usuarioService.entity.Usuario;
import com.example.usuarioService.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST para gestionar las operaciones relacionadas con los usuarios
 *
 * @author Denis, Violeta, Alejandro, Nacho
 * @since 2024-12-10
 * @version 1.0
 */
@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    /**
     * Registra un nuevo usuario
     * @param dtoUsuario contiene la información del usuario a registrar (nombre, apellidos, email, fecha de nacimiento)
     * @return Respuesta HTTP con el estado {@code 201 Created} si el usuario se crea correctamente
     *         El cuerpo de la respuesta contiene los datos del usuario registrado.
     */
    @PostMapping("/registro")
    ResponseEntity<?> registrarUsuario(@RequestBody DtoUsuario dtoUsuario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.saveUsuario(dtoUsuario));
    }
}
