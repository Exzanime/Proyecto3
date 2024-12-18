package com.example.ventaService;

import com.example.ventaService.controller.VentaController;
import com.example.ventaService.dtos.ResponseMessage;
import com.example.ventaService.dtos.VentaRequest;
import com.example.ventaService.service.VentaService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.format.DateTimeFormatter;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@WebMvcTest(VentaController.class)
public class VentaControllerTests {

}
