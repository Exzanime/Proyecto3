package com.example.ventaService;

import com.example.ventaService.dtos.DtoTarjeta;
import com.example.ventaService.dtos.UserValidationResponse;
import com.example.ventaService.dtos.VentaValidationRequest;
import com.example.ventaService.dtos.VentaValidationResponse;
import com.example.ventaService.feignClient.BancoClient;
import com.example.ventaService.feignClient.EventoClient;
import com.example.ventaService.model.Evento;
import com.example.ventaService.model.VentaEntity;
import com.example.ventaService.repository.VentaRepository;
import com.example.ventaService.service.VentaService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class VentaServiceTests {

    @InjectMocks
    private VentaService ventaService;

    @Mock
    private BancoClient bancoClient;

    @Mock
    private EventoClient eventoClient;

    @Mock
    private VentaRepository ventaRepository;



    @Test
    public void testVentaEntradas_ValidScenario() {
        UserValidationResponse userValidationResponse = new UserValidationResponse("Grupo04", "AntoniosRules", "token123");
        when(bancoClient.validarUsuario("Grupo04", "AntoniosRules")).thenReturn(userValidationResponse);

        VentaValidationResponse ventaValidationResponse = new VentaValidationResponse("2023-12-17T12:00:00", "success", null, null, null, "Info adicional");
        when(bancoClient.validarVenta(any(), anyString())).thenReturn(ventaValidationResponse);
        //Evento evento = new Evento();

        Evento evento = new Evento();
        evento.setId(1L);
        evento.setNombre("Concierto Metal");
        evento.setPrecioMin(100.0);
        when(eventoClient.getEventoById(1L)).thenReturn(evento);

        DtoTarjeta tarjeta = new DtoTarjeta();
        tarjeta.setNombreTitular("Juan Pérez");
        tarjeta.setNumero("1234567890123456");
        tarjeta.setCvv("123");
        tarjeta.setMesCaducidad("12");
        tarjeta.setYearCaducidad("25");

        VentaEntity venta = ventaService.ventaEntradas("email@usuario.com", 1L, tarjeta);

        assertNotNull(venta);
        assertEquals("email@usuario.com", venta.getUserEmail());
        assertEquals(1L, venta.getEventoId());
        assertEquals("Concierto Metal", venta.getNombreEvento());
        assertEquals(100.0, venta.getPrecio());
        assertNotNull(venta.getFechaCompra());

        verify(bancoClient).validarUsuario("Grupo04", "AntoniosRules");
        verify(bancoClient).validarVenta(any(), eq("token123"));
        verify(eventoClient).getEventoById(1L);
    }

}
