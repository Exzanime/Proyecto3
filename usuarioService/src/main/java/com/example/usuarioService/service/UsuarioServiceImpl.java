package com.example.usuarioService.service;

import com.example.usuarioService.dto.DtoUsuario;
import com.example.usuarioService.dto.ResponseMessage;
import com.example.usuarioService.entity.Usuario;
import com.example.usuarioService.errors.DuplicadoException;
import com.example.usuarioService.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
        Usuario usuario = Usuario.builder()
                .nombre(dtoUsuario.getNombre())
                .apellido(dtoUsuario.getApellido())
                .email(dtoUsuario.getEmail())
                .fechaNacimiento(dtoUsuario.getFechaNacimiento())
                .build();
        usuarioRepository.save(usuario);
        return dtoUsuario;
    }

    @Override
    public List<ResponseMessage> validate(DtoUsuario dtoUsuario) {
        List<ResponseMessage> errores = new ArrayList<>();
        if(dtoUsuario == null){
            errores.add(ResponseMessage.builder()
                    .message("Usuario no puede ser null")
                    .cause("Se ha proporcionado un usuario null")
                    .status(HttpStatus.BAD_REQUEST)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                    .build());
            return errores;
        }
        if(dtoUsuario.getNombre()=="" && dtoUsuario.getApellido()=="" && dtoUsuario.getEmail()=="" && dtoUsuario.getFechaNacimiento()==null){
            errores.add(ResponseMessage.builder()
                    .message("Usuario no puede ser vacío")
                    .cause("Se ha proporcionado un usuario vacío")
                    .status(HttpStatus.BAD_REQUEST)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                    .build());
            return errores;
        }
        if(dtoUsuario.getNombre()==null){
            errores.add(ResponseMessage.builder()
                    .message("Nombre no puede ser nulo")
                    .cause("No se ha proporcionado un nombre")
                    .status(HttpStatus.BAD_REQUEST)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                    .build());
        }
        if(dtoUsuario.getNombre()==""){
            errores.add(ResponseMessage.builder()
                    .message("Nombre no puede ser vacío")
                    .cause("Se ha proporcionado un nombre vacío")
                    .status(HttpStatus.BAD_REQUEST)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                    .build());
        }
        if(dtoUsuario.getApellido()==""){
            errores.add(ResponseMessage.builder()
                    .message("Apellido no puede ser vacío")
                    .cause("Se ha proporcionado un apellido vacío")
                    .status(HttpStatus.BAD_REQUEST)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                    .build());
        }
        if(dtoUsuario.getApellido()==null){
            errores.add(ResponseMessage.builder()
                    .message("Apellido no puede ser nulo")
                    .cause("No se ha proporcionado un apellido")
                    .status(HttpStatus.BAD_REQUEST)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                    .build());
        }
        if(dtoUsuario.getEmail()==""){
            errores.add(ResponseMessage.builder()
                    .message("Email no puede ser vacío")
                    .cause("Se ha proporcionado un email vacío")
                    .status(HttpStatus.BAD_REQUEST)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                    .build());
        }
        if(dtoUsuario.getEmail()==null){
            errores.add(ResponseMessage.builder()
                    .message("Email no puede ser nulo")
                    .cause("No se ha proporcionado un email")
                    .status(HttpStatus.BAD_REQUEST)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                    .build());
        }
        if(dtoUsuario.getFechaNacimiento()==null){
            errores.add(ResponseMessage.builder()
                    .message("Fecha de nacimiento no puede ser vacía")
                    .cause("Se ha proporcionado una fecha de nacimiento vacía")
                    .status(HttpStatus.BAD_REQUEST)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                    .build());
        }
        if(!isValidateEmail(dtoUsuario.getEmail())){
            errores.add(ResponseMessage.builder()
                    .message("Email no válido")
                    .cause("El email "+dtoUsuario.getEmail()+" no es válido")
                    .status(HttpStatus.BAD_REQUEST)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                    .build());
        }
        if(usuarioRepository.existsByEmail(dtoUsuario.getEmail())){
            errores.add(ResponseMessage.builder()
                    .message("Email duplicado")
                    .cause("El email "+dtoUsuario.getEmail()+" ya está registrado")
                    .status(HttpStatus.BAD_REQUEST)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                    .build());
        }
        return errores;
    }

    @Override
    public boolean isValidateEmail(String email) {
        if(email.split("@").length == 2 && email.split("@")[1].split("\\.").length == 2){
            return true;
        }
        return false;
    }
}
