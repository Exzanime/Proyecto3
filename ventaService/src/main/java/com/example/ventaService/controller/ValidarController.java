package com.example.ventaService.controller;

import com.example.ventaService.dtos.*;
import com.example.ventaService.feignClient.BancoClient;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/validar")
public class ValidarController {

    private final BancoClient bancoClient;
    private static String token;

    public ValidarController(BancoClient bancoClient) {
        this.bancoClient = bancoClient;
    }

    @CircuitBreaker(name = "antonioCB", fallbackMethod = "fallBackValidarUsuario")
    @PostMapping("/usuario")
    public ResponseEntity<?> validarUsuario(@RequestBody UserValidationRequest request) {
        try {
            UserValidationResponse response = bancoClient.validarUsuario(request.getUser(), request.getPwd());
            token = response.getToken();
            return ResponseEntity.ok(response);
        } catch (FeignException e) {
            return ResponseEntity.status(e.status()).body("Error: " + e.getMessage());
        }
    }
    @CircuitBreaker(name = "antonioCB", fallbackMethod = "fallBackValidarVenta")
    @PostMapping("/venta")
    public ResponseEntity<?> validarVenta(@RequestBody VentaValidationRequest request) {
        try {
            VentaValidationResponse response = bancoClient.validarVenta(request, token);
            return ResponseEntity.ok(response);
        } catch (FeignException e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage()+ "Request " +request.toString() + "token " + token);
        }
    }


    private ResponseEntity<?> fallBackValidarUsuario(@RequestBody UserValidationRequest request, RuntimeException e){
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ResponseMessage.builder()
                .message("Error de conexion con el servicio de validacion de usuario, intentelo mas tarde")
                .cause("Parece que estan teniendo problemas en el servicio de antonio")
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .code(HttpStatus.SERVICE_UNAVAILABLE.value())
                .date(LocalDateTime.now())
                //.body(e.getMessage())
                .build());
    }


    private ResponseEntity<?> fallBackValidarVenta(@RequestBody VentaValidationRequest request, RuntimeException e){
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ResponseMessage.builder()
                .message("Error de conexion con el servicio de validacion de venta, intentelo mas tarde")
                .cause("Parece que estan teniendo problemas en el servicio de antonio")
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .code(HttpStatus.SERVICE_UNAVAILABLE.value())
                .date(LocalDateTime.now())
                //.body(e.getMessage())
                .build());
    }
}

