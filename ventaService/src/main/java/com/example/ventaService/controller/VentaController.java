package com.example.ventaService.controller;

import com.example.ventaService.dtos.DtoTarjeta;
import com.example.ventaService.model.VentaEntity;
import com.example.ventaService.dtos.DtoVenta;
import com.example.ventaService.dtos.ResponseMessage;
import com.example.ventaService.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/venta")
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
    @PostMapping("/registro")
    ResponseEntity<?> registrarUsuario(@RequestBody DtoVenta dtoVenta) {
        List<ResponseMessage> errores = ventaService.validatePost(dtoVenta);
        if (!errores.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseMessage.builder()
                    .message("Error en la petición")
                    .cause("La petición no es válida")
                    .status(HttpStatus.BAD_REQUEST)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                    .body(errores)
                    .build());
        }else {
            return ResponseEntity.status(HttpStatus.CREATED).body(ResponseMessage.builder()
                    .message("Venta registrada")
                    .cause("La venta realizada el "+dtoVenta.getFechaCompra()+" para el evento "+dtoVenta.getNombreEvento()+" ha sido registrada correctamente")
                    .status(HttpStatus.CREATED)
                    .code(HttpStatus.CREATED.value())
                    .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                    .body(ventaService.saveVenta(dtoVenta))
                    .build());
        }
    }
}
