package com.example.usuarioService.service;

import com.example.usuarioService.dto.DtoUsuario;
import com.example.usuarioService.entity.Usuario;

/**
 * Servicio para gestionar las operaciones relacionadas con usuarios.
 * <p>
 * Define los métodos que deben ser implementados para manejar la lógica de negocio
 * asociada a los usuarios, incluyendo su creación, actualización, y otras operaciones.
 * @author denis, violeta, alejandro, nacho
 * @since 2024-12-10
 * @version 1.0
 */
public interface UsuarioService {
    public DtoUsuario saveUsuario(DtoUsuario dtoUsuario);
}