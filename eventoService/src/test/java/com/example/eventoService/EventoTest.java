package com.example.eventoService;

import com.example.eventoService.repository.EventoRepository;
import com.example.eventoService.service.EventoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

@SpringBootTest
public class EventoTest {
    @Mock
    EventoRepository eventoRepository;
    @InjectMocks
    EventoServiceImpl eventoService;
    @BeforeEach
    public void setUp() {
        openMocks(this);
    }
    @Test
    public void testGetDetallesEvento() {
        when(eventoRepository.findById(1L)).thenReturn(null);
        eventoService.getDetalleEvento(1L);
        assert(eventoService.getDetalleEvento(1L)==null);
    }
}
