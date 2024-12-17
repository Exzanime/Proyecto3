package com.example.ventaService;

import com.example.ventaService.feignClient.EventoClient;
import com.example.ventaService.model.Evento;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
public class FeignClientTests {


    @Mock
    private EventoClient mockEventoClient; // Mock del cliente para simular respuestas

    @Test
    public void testGetEventoById() {

        Evento mockResponse = new Evento();
        mockResponse.setId(1L);
        mockResponse.setNombre("Concierto de Rock");
        mockResponse.setDescripcion("Un evento para amantes del rock.");
        mockResponse.setGenero("Música");
        mockResponse.setLocalidad("Madrid");
        mockResponse.setRecinto("Estadio Metropolitano");
        mockResponse.setFecha("2024-12-31T21:00:00");
        mockResponse.setPrecioMin(30.50);
        mockResponse.setPrecioMax(150.75);
        when(mockEventoClient.getEventoById(anyLong())).thenReturn(mockResponse);


        Evento actualResponse = mockEventoClient.getEventoById(1L);


        assertEquals(mockResponse.getId(), actualResponse.getId());
        assertEquals(mockResponse.getNombre(), actualResponse.getNombre());
        assertEquals(mockResponse.getFecha(), actualResponse.getFecha());
    }


}
