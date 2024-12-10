package com.example.usuarioService.service;

import com.example.usuarioService.dto.DtoUsuario;
import com.example.usuarioService.entity.Usuario;
import com.example.usuarioService.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public DtoUsuario saveUsuario(DtoUsuario dtoUsuario) {
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
