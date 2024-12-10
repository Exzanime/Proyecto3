package com.example.eventoService;

import com.example.eventoService.entity.Evento;
import com.example.eventoService.repository.EventoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class EventoRepositoryTest {
    @Autowired
    private EventoRepository eventoRepository;

    //Prueba para conectar con la base de datos
    @Test
    public void testGuardarYRecuperarEvento() {
        Evento evento = Evento.builder()
                .nombre("Concierto Rock")
                .genero("Rock")
                .localidad("Barcelona")
                .recinto("Estadio Olímpico")
                .descripcion("Un gran concierto de rock")
                .fecha(LocalDateTime.now().plusDays(5))
                .precioMin(50.0)
                .precioMax(150.0)
                .build();

        Evento eventoGuardado = eventoRepository.save(evento);

        Optional<Evento> eventoRecuperado = eventoRepository.findById(eventoGuardado.getId());
        assertThat(eventoRecuperado).isPresent();
        assertThat(eventoRecuperado.get().getNombre()).isEqualTo("Concierto Rock");
        assertThat(eventoRecuperado.get().getGenero()).isEqualTo("Rock");
    }
}
