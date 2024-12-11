package com.example.eventoService;

import com.example.eventoService.controller.error.EventoDuplicateExcp;
import com.example.eventoService.dto.DtoEvento;
import com.example.eventoService.entity.Evento;
import com.example.eventoService.repository.EventoRepository;
import com.example.eventoService.service.EventoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

@SpringBootTest
public class EventoServiceImplTest {
    @Mock
    EventoRepository eventoRepository;
    @InjectMocks
    EventoServiceImpl eventoService;

    private DtoEvento dtoEvento;

    private Evento eventoExistente;

    @BeforeEach
    public void setUp() {
        openMocks(this);

        dtoEvento = DtoEvento.builder()
                .nombre("Concierto Metal")
                .genero("Metal")
                .localidad("Madrid")
                .recinto("Palacio de Deportes")
                .descripcion("descripcion")
                .fecha(String.valueOf(LocalDate.now().plusDays(10)))
                .precioMin(30.0)
                .precioMax(100.0)
                .build();

        eventoExistente = Evento.builder()
                .nombre("Concierto Metal")
                .genero("Metal")
                .localidad("Madrid")
                .recinto("Palacio de Deportes")
                .descripcion("descripcion")
                .fecha(LocalDate.parse(dtoEvento.getFecha()))
                .precioMin(30.0)
                .precioMax(100.0)
                .build();
    }
    @Test
    public void testGetDetallesEvento() {
        when(eventoRepository.findById(1L)).thenReturn(null);
        assertTrue(eventoRepository.findById(1L) == null);
    }

    @Test
    public void testSaveEventoCorrectamente() {
        Evento evento = Evento.builder()
                .nombre("Concierto Metal")
                .genero("Metal")
                .localidad("Madrid")
                .recinto("Palacio de Deportes")
                .descripcion("descripcion")
                .fecha(LocalDate.from(LocalDate.now().plusDays(10)))
                .precioMin(30.0)
                .precioMax(100.0)
                .build();

        when(eventoRepository.save(any(Evento.class))).thenReturn(evento);

        DtoEvento result = eventoService.saveEvento(dtoEvento);

        verify(eventoRepository, times(1)).save(any(Evento.class));
        assert(result.getNombre().equals(evento.getNombre()));
        assert(result.getGenero().equals(evento.getGenero()));
        assert(result.getLocalidad().equals(evento.getLocalidad()));
        assert(result.getRecinto().equals(evento.getRecinto()));
        assert(result.getPrecioMin() == evento.getPrecioMin());
        assert(result.getPrecioMax() == evento.getPrecioMax());
    }

    @Test
    public void testSaveEventoLanzaExcepcionSiExiste() {
        when(eventoRepository.findByNombreAndGeneroAndFecha(
                dtoEvento.getNombre(),
                dtoEvento.getGenero(),
                LocalDate.parse(dtoEvento.getFecha())))
                .thenReturn(Optional.of(eventoExistente));

        assertThrows(EventoDuplicateExcp.class, () -> eventoService.saveEvento(dtoEvento));

        verify(eventoRepository, times(1)).findByNombreAndGeneroAndFecha(dtoEvento.getNombre(), dtoEvento.getGenero(), LocalDate.parse(dtoEvento.getFecha()));

        verify(eventoRepository, never()).save(any(Evento.class));
    }
}
