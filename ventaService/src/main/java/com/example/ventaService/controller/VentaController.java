package com.example.ventaService.controller;

import com.example.ventaService.dtos.DtoTarjeta;
import com.example.ventaService.model.VentaEntity;
import com.example.ventaService.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {
    @Autowired
    private VentaService ventaService;

    @PostMapping("/compra")
    public ResponseEntity<?> ventaEntradas(@RequestParam String emailUsuario, @RequestParam Long idEvento, @RequestParam DtoTarjeta tarjeta){
        try {
            VentaEntity venta = ventaService.ventaEntradas(emailUsuario, idEvento, tarjeta);
            return ResponseEntity.ok(venta);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
