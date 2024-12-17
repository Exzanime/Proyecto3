package com.example.ventaService.controller;

import com.example.ventaService.dtos.UserValidationRequest;
import com.example.ventaService.dtos.UserValidationResponse;
import com.example.ventaService.dtos.VentaValidationRequest;
import com.example.ventaService.dtos.VentaValidationResponse;
import com.example.ventaService.feignClient.BancoClient;
import feign.FeignException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/validar")
public class ValidarController {

    private final BancoClient bancoClient;
    private String token;

    public ValidarController(BancoClient bancoClient) {
        this.bancoClient = bancoClient;
    }

    @PostMapping("/usuario")
    public ResponseEntity<?> validarUsuario(@RequestBody UserValidationRequest request) {
        try {
            UserValidationResponse response = bancoClient.validarUsuario(request.getUser(), request.getPwd());
            return ResponseEntity.ok(response);
        } catch (FeignException e) {
            return ResponseEntity.status(e.status()).body("Error: " + e.getMessage());
        }
    }
    @PostMapping("/venta")
    public ResponseEntity<?> validarVenta(@RequestBody VentaValidationRequest request,@RequestHeader("Authorization") String jwtToken) {
        try {
            VentaValidationResponse response = bancoClient.validarVenta(request, jwtToken);
            return ResponseEntity.ok(response);
        } catch (FeignException e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }
}

