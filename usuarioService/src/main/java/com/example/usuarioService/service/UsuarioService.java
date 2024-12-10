package com.example.usuarioService.service;

import com.example.usuarioService.dto.DtoUsuario;
import com.example.usuarioService.entity.Usuario;

public interface UsuarioService {
    public DtoUsuario saveUsuario(DtoUsuario dtoUsuario);
}
