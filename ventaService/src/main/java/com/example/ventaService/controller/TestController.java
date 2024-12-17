package com.example.ventaService.controller;

import com.example.ventaService.dtos.UserValidationRequest;
import com.example.ventaService.dtos.UserValidationResponse;
import com.example.ventaService.feignClient.BancoClient;
import com.example.ventaService.feignClient.UsuarioClient;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {

    private final BancoClient bancoClient;

    public TestController(BancoClient bancoClient) {
        this.bancoClient = bancoClient;
    }

    @PostMapping("/validar")
    public ResponseEntity<?> validarUsuario(@RequestBody UserValidationRequest request) {
        try {
            UserValidationResponse response = bancoClient.validarUsuario(request.getUser(), request.getPwd());
            return ResponseEntity.ok(response);
        } catch (FeignException e) {
            return ResponseEntity.status(e.status()).body("Error: " + e.getMessage());
        }
    }
}

