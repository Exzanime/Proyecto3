package com.example.usuarioService.controller;

import java.time.LocalDateTime;
import com.example.usuarioService.dto.DtoUsuario;
import com.example.usuarioService.dto.ResponseMessage;
import com.example.usuarioService.entity.Usuario;
import com.example.usuarioService.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

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
        List<ResponseMessage> errores = usuarioService.validate(dtoUsuario);
        if (!errores.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseMessage.builder()
                    .message("Error en la petición")
                    .cause("La petición no es válida")
                    .status(HttpStatus.BAD_REQUEST)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                    .body(errores)
                    .build());
        }else {
            return ResponseEntity.status(HttpStatus.CREATED).body(ResponseMessage.builder()
                    .message("Usuario creado")
                    .cause("El usuario "+dtoUsuario.getNombre()+" ha sido creado correctamente")
                    .status(HttpStatus.CREATED)
                    .code(HttpStatus.CREATED.value())
                    .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                    .body(usuarioService.saveUsuario(dtoUsuario))
                    .build());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUsuario(@RequestBody DtoUsuario dtoUsuario, @PathVariable Long id) {
        //DtoUsuario optionalDtoUsuario = usuarioService.getDetalleUsuario(id);
        List<ResponseMessage> errores = usuarioService.validate(dtoUsuario);
        if (!errores.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseMessage.builder()
                    .message("Error en la petición")
                    .cause("La petición no es válida")
                    .status(HttpStatus.BAD_REQUEST)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                    .body(errores)
                    .build());
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(ResponseMessage.builder()
                    .message("Usuario actualizado")
                    .cause("El usuario " + dtoUsuario.getNombre() + " ha sido actualizado correctamente")
                    .status(HttpStatus.CREATED)
                    .code(HttpStatus.CREATED.value())
                    .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                    .body(usuarioService.saveUsuario(dtoUsuario))
                    .build());
        }
    }
}
