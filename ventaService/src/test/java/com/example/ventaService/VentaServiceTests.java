package com.example.ventaService;

import com.example.ventaService.dtos.UserValidationResponse;
import com.example.ventaService.dtos.VentaValidationResponse;
import com.example.ventaService.feignClient.BancoClient;
import com.example.ventaService.feignClient.EventoClient;
import com.example.ventaService.model.Evento;
import com.example.ventaService.model.VentaEntity;
import com.example.ventaService.repository.VentaRepository;
import com.example.ventaService.service.VentaServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

//@SpringBootTest
public class VentaServiceTests {

    @InjectMocks
    private VentaServiceImpl ventaService;

    @Mock
    private BancoClient bancoClient;

    @Mock
    private EventoClient eventoClient;

    @Mock
    private VentaRepository ventaRepository;

}
