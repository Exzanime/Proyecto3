package com.example.eventoService.controller;

import com.example.eventoService.dto.DtoEvento;
import com.example.eventoService.dto.ResponseMessage;
import com.example.eventoService.service.EventoService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/evento")
public class EventoController {
    @Autowired
    EventoService eventoService;

    @PostMapping("/registro")
    ResponseEntity<?> addEvento(@RequestBody DtoEvento dtoEvento){
        List<ResponseMessage> errores = eventoService.validate(dtoEvento,false);
        if(!errores.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseMessage.builder()
                    .message("Error en la petición")
                    .cause("La petición no es válida")
                    .status(HttpStatus.BAD_REQUEST)
                    .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                    .code(HttpStatus.BAD_REQUEST.value())
                    .body(errores)
                    .build());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseMessage.builder()
                .message("Evento creado")
                .cause("El evento "+dtoEvento.getNombre()+" ha sido creado correctamente")
                .status(HttpStatus.CREATED)
                .code(HttpStatus.CREATED.value())
                .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .body(eventoService.saveEvento(dtoEvento))
                .build());
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
    @PutMapping("/update/{id}")
    ResponseEntity<?> updateEvento(@PathVariable Long id, @RequestBody DtoEvento dtoEvento){
        dtoEvento.setId(id);
        List<ResponseMessage> errores = eventoService.validate(dtoEvento,true);
        if(!errores.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseMessage.builder()
                    .message("Error en la petición")
                    .cause("La petición no es válida")
                    .status(HttpStatus.BAD_REQUEST)
                    .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                    .code(HttpStatus.BAD_REQUEST.value())
                    .body(errores)
                    .build());
        }
        if(eventoService.updateEvento(id, dtoEvento)==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseMessage.builder()
                    .message("Evento no encontrado")
                    .cause("No se ha encontrado el evento con id: "+id)
                    .status(HttpStatus.NOT_FOUND)
                    .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                    .build());
        }
        return ResponseEntity.status(HttpStatus.OK).body(ResponseMessage.builder()
                .message("Evento actualizado")
                .cause("El evento "+dtoEvento.getNombre()+" ha sido actualizado correctamente")
                .status(HttpStatus.OK)
                .code(HttpStatus.OK.value())
                .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .body(eventoService.updateEvento(id, dtoEvento))

                .build());
    }

    @GetMapping("/name/{nombre}")
    public ResponseEntity<?> getEventoByName(@PathVariable String nombre){
        if(eventoService.findByNombre(nombre).isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseMessage.builder()
                    .message("Evento no encontrado")
                    .cause("No se encontro un evento con el nombre: "+ nombre)
                    .status(HttpStatus.NOT_FOUND)
                    .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                    .build()
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(eventoService.findByNombre(nombre));
    }

    /**
     * Elimina un Evento/DtoEvento del repositorio en el endpoint /{id}, y devuelve la información pertinente
     * según el resultado
     *
     * @param id Long autoincremental en los objetos Evento y DtoEvento
     * @return ResponseEntity que contiene un ReponseMessage: para casos en los que el evento no se encuentra
     * (HTTP 404 NOT FOUND) y para los que la eliminación se realiza sin problemas (HTTP 200 OK)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvento(@PathVariable Long id) {
        DtoEvento dtoEvento = eventoService.getDetalleEvento(id);
        if (dtoEvento == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    ResponseMessage.builder()
                            .message("Evento no encontrado.")
                            .cause("No ha sido encontrado el evento con id " + id + ".")
                            .status(HttpStatus.NOT_FOUND)
                            .code(HttpStatus.NOT_FOUND.value())
                            .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                            .build()
             );
        }
        String nombreEvento = dtoEvento.getNombre();
        eventoService.deleteById(id);
        return ResponseEntity.ok(
                ResponseMessage.builder()
                        .message("El evento se ha eliminado con éxito.")
                        .cause("El evento con el ID " + id + ", (" + nombreEvento + ")," + "se ha eliminado.")
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                        .build()
        );
    }

}
