package com.example.eventoService.service;

import com.example.eventoService.dto.DtoEvento;
import com.example.eventoService.dto.ResponseMessage;
import com.example.eventoService.entity.Evento;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz que define los métodos que se pueden realizar sobre los juegos
 * @autor Violeta,Nacho,Denis, Alejandro
 * @version 1.0
 * @date 2024
 */
public interface EventoService {

    DtoEvento saveEvento(DtoEvento dtoEvento);
    DtoEvento getDetalleEvento(Long id);
    List<DtoEvento> listarEventos();
    void deleteById(Long id);
    List<ResponseMessage> validate(DtoEvento dtoEvento);
    DtoEvento updateEvento(Long id,DtoEvento dtoEvento);
    List<DtoEvento> findByNombre(String nombre);
}
