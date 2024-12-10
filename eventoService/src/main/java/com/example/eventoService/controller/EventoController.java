package com.example.eventoService.controller;

import com.example.eventoService.dto.DtoEvento;
import com.example.eventoService.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/evento")
public class EventoController {
    @Autowired
    EventoService eventoService;

    @PostMapping("/registro")
    ResponseEntity<?> addEvento(@RequestBody DtoEvento dtoEvento){

        return ResponseEntity.status(HttpStatus.CREATED).body(eventoService.saveEvento(dtoEvento));
    }
}
