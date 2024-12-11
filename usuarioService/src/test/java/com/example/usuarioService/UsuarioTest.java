package com.example.usuarioService;

import com.example.usuarioService.dto.DtoUsuario;
import com.example.usuarioService.entity.Usuario;
import com.example.usuarioService.exception.DuplicadoException;
import com.example.usuarioService.repository.UsuarioRepository;
import com.example.usuarioService.service.UsuarioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UsuarioTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    private DtoUsuario dtoUsuario;
    private Usuario usuarioExistente;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dtoUsuario = DtoUsuario.builder()
                .nombre("Juan")
                .apellido("Juan")
                .email("juan@gmail.com")
                .fechaNacimiento(LocalDate.of(1999,1,23))
                .build();

        usuarioExistente = Usuario.builder()
                .id(1L)
                .nombre("Juan")
                .apellido("Perez")
                .email(dtoUsuario.getEmail())
                .fechaNacimiento(dtoUsuario.getFechaNacimiento())
                .build();
    }

    @Test
    void testaddUsuario() {
        when(usuarioRepository.existsByEmail(dtoUsuario.getEmail())).thenReturn(false);
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> {
            Usuario usuario = invocation.getArgument(0);
            usuario.setId(1L);
            return usuario;
        });
        DtoUsuario resultado = usuarioService.saveUsuario(dtoUsuario);

        verify(usuarioRepository,times(1)).existsByEmail(dtoUsuario.getEmail());
        verify(usuarioRepository,times(1)).save(any(Usuario.class));

        assertNotNull(resultado);
        assertEquals(dtoUsuario.getNombre(), resultado.getNombre());
        assertEquals(dtoUsuario.getEmail(), resultado.getEmail());
    }


    @Test
    void testAddUsuarioLanzaExceptionSiExisteEmail(){
        when(usuarioRepository.existsByEmail(dtoUsuario.getEmail())).thenReturn(true);

        DuplicadoException exception = assertThrows(DuplicadoException.class, () -> usuarioService.saveUsuario(dtoUsuario));

        assertEquals("Email " + dtoUsuario.getEmail() + " ya existe", exception.getMessage());

        verify(usuarioRepository,times(1)).existsByEmail(dtoUsuario.getEmail());
        verify(usuarioRepository,never()).save(any(Usuario.class));
    }


}
