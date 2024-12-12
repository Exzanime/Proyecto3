package com.example.eventoService;

import com.example.eventoService.controller.EventoController;
import com.example.eventoService.dto.DtoEvento;
import com.example.eventoService.service.EventoService;
import com.example.eventoService.service.EventoServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.mock;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EventoController.class)
@Import(EventoControllerTest.MockConfiguration.class)
public class EventoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private EventoService eventoService;
    static class MockConfiguration {
        @Bean
        public EventoService eventoService() {
            return mock(EventoService.class); // Mock manual del servicio
        }
    }
    @Test
    void addEventoDevuelveCreatedWhenIsValid() throws Exception {
        DtoEvento dtoEvento = DtoEvento.builder()
                .nombre("Concierto Rock")
                .descripcion("Concierto en vivo de bandas locales")
                .genero("Rock")
                .localidad("Madrid")
                .recinto("Wanda Metropolitano")
                .fecha("15/12/2024")
                .precioMin(30.50)
                .precioMax(100.00)
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/evento/registro")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoEvento)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Evento creado"))
                .andExpect(jsonPath("$.cause").value("El evento Concierto Rock ha sido creado correctamente"))
                .andExpect(jsonPath("$.status").value(HttpStatus.CREATED.name()));
    }

    @Test
    void addEventoDevuelveBadRequestWhenInputIsInvalid() throws Exception {
        DtoEvento dtoEvento = DtoEvento.builder()
                .nombre("")
                .descripcion("Concierto en vivo de bandas locales")
                .genero("Rock")
                .localidad("Madrid")
                .recinto("Wanda Metropolitano")
                .fecha("15/12/2024")
                .precioMin(30.50)
                .precioMax(100.00)
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/registro")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoEvento)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Error interno. Hubo un problema al procesar la solicitud. Inténtalo de nuevo más tarde."))
                .andExpect(jsonPath("$.cause").value("No static resource registro."))
                .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.name()));
    }
}
