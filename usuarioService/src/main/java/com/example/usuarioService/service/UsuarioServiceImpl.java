package com.example.usuarioService.service;

import com.example.usuarioService.dto.DtoUsuario;
import com.example.usuarioService.entity.Usuario;
import com.example.usuarioService.exception.DuplicadoException;
import com.example.usuarioService.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementación del servicio UsuarioService para gestionar la lógica relacionada con usuarios.
 * @author denis, alejandro, violeta, nacho
 * @since 2024-12-10
 * @version 1.0
 */
@Service
public class UsuarioServiceImpl implements UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Guarda un nuevo usuario.
     * Antes de guardar, valida que el email no esté ya registrado. Si el correo exista,
     * lanza una excepción de la clase DuplicadoException
     * @param dtoUsuario Contiene la información del usuario
     * @return Un objeto DtoUsuario que representa al usuario guardado
     */
    @Override
    public DtoUsuario saveUsuario(DtoUsuario dtoUsuario) {
        if(usuarioRepository.existsByEmail(dtoUsuario.getEmail())){
            throw new DuplicadoException("Email " + dtoUsuario.getEmail() + " ya existe");
        }
        Usuario usuario = Usuario.builder()
                .nombre(dtoUsuario.getNombre())
                .apellido(dtoUsuario.getApellido())
                .email(dtoUsuario.getEmail())
                .fechaNacimiento(dtoUsuario.getFechaNacimiento())
                .build();
        usuarioRepository.save(usuario);
        return dtoUsuario;
    }
}
