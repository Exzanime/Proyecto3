package com.example.ventaService.controller;

import com.example.ventaService.dtos.VentaRequest;
import com.example.ventaService.model.VentaEntity;
import com.example.ventaService.dtos.DtoVenta;
import com.example.ventaService.dtos.ResponseMessage;
import com.example.ventaService.service.VentaService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/venta")
public class VentaController {
    @Autowired
    private VentaService ventaService;

    @CircuitBreaker(name = "eventoCB", fallbackMethod = "fallBackVentaEntradas")
    @PostMapping("/compra")
    public ResponseEntity<?> ventaEntradas(@RequestBody VentaRequest ventaRequest){
        List<ResponseMessage> errores = ventaService.validateVenta(ventaRequest);
        if(!errores.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseMessage.builder()
                    .message("Error en la petición")
                    .cause("La petición no es válida")
                    .status(HttpStatus.BAD_REQUEST)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .date(LocalDateTime.now())
                    .body(errores)
                    .build());
        }else {
            VentaEntity venta = ventaService.ventaEntradas(
                    VentaRequest.builder()
                            .userEmail(ventaRequest.getUserEmail())
                            .eventoId(ventaRequest.getEventoId())
                            .numero(ventaRequest.getNumero())
                            .precio(ventaRequest.getPrecio())
                            .cvv(ventaRequest.getCvv())
                            .yearCaducidad(ventaRequest.getYearCaducidad())
                            .usuarioId(ventaRequest.getUsuarioId())
                            .mesCaducidad(ventaRequest.getMesCaducidad())
                            .nombreTitular(ventaRequest.getNombreTitular())
                            .build()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(ResponseMessage.builder()
                    .message("Venta realizada")
                    .cause("La venta realizada el "+venta.getFechaCompra()+" para el evento "+venta.getEventoId()+" ha sido registrada correctamente")
                    .status(HttpStatus.CREATED)
                    .code(HttpStatus.CREATED.value())
                    .date(LocalDateTime.now())
                    .body(venta)
                    .build());
        }
    }
    @GetMapping("/entradas/{email}")
    public ResponseMessage getVentasByUserEmail(@PathVariable String email){
        List<DtoVenta> ventas = ventaService.getVentasByUserEmail(email);
        if(ventas.isEmpty()){
            return ResponseMessage.builder()
                    .message("No se han encontrado ventas")
                    .cause("No se han encontrado ventas para el usuario con email: "+email)
                    .status(HttpStatus.NOT_FOUND)
                    .date(LocalDateTime.now())
                    .build();
        }else {
            return ResponseMessage.builder()
                    .message("Ventas encontradas")
                    .cause("Se han encontrado "+ventas.size()+" ventas para el usuario con email: "+email)
                    .status(HttpStatus.OK)
                    .code(HttpStatus.OK.value())
                    .date(LocalDateTime.now())
                    .body(ventas)
                    .build();
        }
    }

    private ResponseEntity<?> fallBackVentaEntradas(@RequestBody VentaRequest ventaRequest, RuntimeException e){
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ResponseMessage.builder()
                .message("Error de conexión con el servicio, no se ha podido conectar con la base de datos de eventos, intentelo mas tarde")
                .cause("tas olvidau de arrancar el servicio de eventos empanao")
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .code(HttpStatus.SERVICE_UNAVAILABLE.value())
                .date(LocalDateTime.now())
               //.body(e.getMessage())
                .build());
    }
}
