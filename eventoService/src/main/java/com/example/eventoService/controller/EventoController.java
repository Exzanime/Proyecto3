package com.example.eventoService.controller;

import com.example.eventoService.dto.DtoEvento;
import com.example.eventoService.dto.ResponseMessage;
import com.example.eventoService.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseMessage.builder()
                    .message("Evento no encontrado")
                    .cause("No se ha encontrado el evento con id: "+id)
                    .status(HttpStatus.NOT_FOUND)
                    .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                    .build());
        }
        return ResponseEntity.status(HttpStatus.OK).body(eventoService.getDetalleEvento(id));
    }

    /**
     * Devuelve todos los eventos existentes en la base de datos
     * @return Lista de Dto de la entidad Evento
     */
    @GetMapping("/eventos")
    public ResponseEntity<List<DtoEvento>> listarEventos(){
        List<DtoEvento> listaTodosLosEventos = eventoService.listarEventos();
        return ResponseEntity.ok(listaTodosLosEventos);
    }
}
