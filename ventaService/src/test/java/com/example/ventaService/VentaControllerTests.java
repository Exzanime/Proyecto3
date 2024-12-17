package com.example.ventaService;

import com.example.ventaService.controller.VentaController;
import com.example.ventaService.dtos.ResponseMessage;
import com.example.ventaService.dtos.VentaRequest;
import com.example.ventaService.service.VentaService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.format.DateTimeFormatter;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class VentaControllerTests {

    @Mock
    VentaController ventaController;
    @Test
    void testVentaEntradasConErrores() {
        VentaService mockVentaService = mock(VentaService.class);

        VentaRequest ventaRequest = new VentaRequest();
        ventaRequest.setUserEmail("testuser@example.com");
        ventaRequest.setEventoId(1L);

        when(mockVentaService.validateVenta(ventaRequest)).thenReturn(Collections.singletonList(new ResponseMessage<>("Error", "Invalid request", HttpStatus.BAD_REQUEST, Collections.emptyList())));

        ResponseEntity<?> response = ventaController.ventaEntradas(ventaRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody() instanceof ResponseMessage);
        ResponseMessage<?> body = (ResponseMessage<?>) response.getBody();
        assertEquals("Error en la petición", body.getMessage());
        assertEquals("La petición no es válida", body.getCause());
    }

}
