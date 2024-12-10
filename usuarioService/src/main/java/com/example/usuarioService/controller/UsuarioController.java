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

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/usuarios/registro")
    ResponseEntity<?> registrarUsuario(@RequestBody DtoUsuario dtoUsuario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.saveUsuario(dtoUsuario));
    }
}
