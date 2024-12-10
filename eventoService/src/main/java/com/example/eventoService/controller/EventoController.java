package com.example.eventoService.controller;

import com.example.eventoService.dto.DtoEvento;
import com.example.eventoService.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/evento")
public class EventoController {
    @Autowired
    EventoService eventoService;

    @PostMapping("/registro")
    ResponseEntity<?> addEvento(@RequestBody DtoEvento dtoEvento){

        return ResponseEntity.status(HttpStatus.CREATED).body(eventoService.saveEvento(dtoEvento));
    }
    @GetMapping("/details/{id}")
    ResponseEntity<?> getDetalles(@PathVariable Long id){
        if(eventoService.getDetalleEvento(id)==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se ha encontrado el evento");
        }
        return ResponseEntity.status(HttpStatus.OK).body(eventoService.getDetalleEvento(id));
    }
}
