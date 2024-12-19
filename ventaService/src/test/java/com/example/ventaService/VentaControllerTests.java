package com.example.ventaService;

import com.example.ventaService.controller.VentaController;
import com.example.ventaService.dtos.VentaRequest;
import com.example.ventaService.service.VentaService;
import com.example.ventaService.service.VentaServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VentaController.class)
public class VentaControllerTests {
        @MockitoBean
        private VentaService ventaService;
        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;
        @Test
        void ventaEntradasShouldReturnOkWhenInputIsValid() throws Exception {
            VentaRequest ventaRequest = VentaRequest.builder()
                    .userEmail("ejemplo@gmail.com")
                    .precio(10.0)
                    .numero(String.valueOf(1))
                    .cvv(String.valueOf(123))
                    .eventoId(1L)
                    .usuarioId(1L)
                    .nombreTitular("Ejemplo")
                    .mesCaducidad(String.valueOf(12))
                    .yearCaducidad(String.valueOf(2025))
                    .build();

            mockMvc.perform(post("/venta/compra")
                            .contentType("application/json")
                            .content(objectMapper.writeValueAsString(ventaRequest)))
                    .andExpect(status().isOk());
        }

}
