package com.example.ventaService;
import com.example.ventaService.feignClient.EventoClient;
import com.example.ventaService.model.Evento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class FeignClientTests {

    @Mock
    private EventoClient eventoClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetEventoByIdReturnValidEvento() {
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

        when(eventoClient.getEventoById(anyLong())).thenReturn(mockResponse);

        Evento actualResponse = eventoClient.getEventoById(1L);

        assertEquals(mockResponse.getId(), actualResponse.getId());
        assertEquals(mockResponse.getNombre(), actualResponse.getNombre());
        assertEquals(mockResponse.getFecha(), actualResponse.getFecha());
        assertEquals(mockResponse.getPrecioMin(), actualResponse.getPrecioMin());
    }
}
