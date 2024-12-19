package com.example.ventaService;

import com.example.ventaService.dtos.*;
import com.example.ventaService.feignClient.BancoClient;
import com.example.ventaService.feignClient.EventoClient;
import com.example.ventaService.feignClient.UsuarioClient;
import com.example.ventaService.model.Evento;
import com.example.ventaService.model.Usuario;
import com.example.ventaService.model.VentaEntity;
import com.example.ventaService.repository.VentaRepository;
import com.example.ventaService.service.VentaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class VentaServiceTests {

    @Mock
    private BancoClient bancoClient;

    @Mock
    private EventoClient eventoClient;

    @Mock
    private VentaRepository ventaRepository;
    @Mock
    private UsuarioClient usuarioClient;

    @InjectMocks
    private VentaServiceImpl ventaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void ventaEntradasShouldCreateVentaWhenRequestIsValid() {
        VentaRequest request = VentaRequest.builder()
                .userEmail("test@example.com")
                .eventoId(1L)
                .numero("1234567890123456")
                .precio(100.0)
                .cvv("123")
                .mesCaducidad("12")
                .yearCaducidad("2025")
                .nombreTitular("John Doe")
                .build();

        UserValidationResponse userValidationResponse = new UserValidationResponse();
        userValidationResponse.setToken("validToken");

        VentaValidationResponse ventaValidationResponse = new VentaValidationResponse();

        VentaEntity savedVenta = new VentaEntity();
        savedVenta.setFechaCompra(LocalDateTime.now());
        savedVenta.setEventoId(1L);

        when(bancoClient.validarUsuario(any(), any())).thenReturn(userValidationResponse);
        when(bancoClient.validarVenta(any(), any())).thenReturn(ventaValidationResponse);
        when(eventoClient.getEventoById(any())).thenReturn(new Evento(1L, "Test Event", 50.0));
        when(ventaRepository.save(any(VentaEntity.class))).thenReturn(savedVenta);

        VentaEntity result = ventaService.ventaEntradas(request);

        assertNotNull(result);
        assertEquals(1L, result.getEventoId());
        verify(ventaRepository, times(1)).save(any(VentaEntity.class));
    }

    @Test
    void ventaEntradasShouldThrowExceptionWhenUserValidationFails() {
        VentaRequest request = VentaRequest.builder()
                .userEmail("test@example.com")
                .build();

        when(bancoClient.validarUsuario(any(), any())).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> ventaService.ventaEntradas(request));
        assertEquals("Usuario no válido o no autorizado", exception.getMessage());
    }

    @Test
    void ventaEntradasShouldThrowExceptionWhenEventoNotFound() {
        VentaRequest request = VentaRequest.builder()
                .eventoId(1L)
                .build();

        when(bancoClient.validarUsuario(any(), any())).thenReturn(UserValidationResponse.builder()
                .token("validToken")
                .pwd("password")
                .user("user")
                .build());
        when(bancoClient.validarVenta(any(), any())).thenReturn(new VentaValidationResponse());
        when(eventoClient.getEventoById(any())).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> ventaService.ventaEntradas(request));
        assertEquals("Evento no encontrado", exception.getMessage());
    }

    @Test
    void getVentasByUserEmailShouldReturnEmptyListWhenNoVentasExist() {
        String email = "nonexistent@example.com";

        when(ventaRepository.findVentasByUserEmail(email)).thenReturn(Collections.emptyList());

        List<DtoVenta> result = ventaService.getVentasByUserEmail(email);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getVentasByUserEmailShouldReturnVentasWhenExists() {
        String email = "test@example.com";

        VentaEntity ventaEntity = new VentaEntity();
        ventaEntity.setUsuarioId(1L);
        ventaEntity.setUserEmail(email);
        ventaEntity.setEventoId(1L);

        when(ventaRepository.findVentasByUserEmail(email)).thenReturn(List.of(ventaEntity));

        List<DtoVenta> result = ventaService.getVentasByUserEmail(email);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(email, result.get(0).getUserEmail());
    }
    @Test
    void checkUsuarioClientConectWell() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Juan");
        usuario.setEmail("juan@gmail.com");

        when(usuarioClient.validarUsuario(UserValidationRequest.builder().build())).thenReturn(UserValidationResponse.builder().token("").build());
        assertTrue(usuarioClient.validarUsuario(UserValidationRequest.builder().build()).getToken().isEmpty());

    }
}
