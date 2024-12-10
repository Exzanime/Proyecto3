package com.example.eventoService.service;

import com.example.eventoService.dto.DtoEvento;
import com.example.eventoService.entity.Evento;

/**
 * Interfaz que define los métodos que se pueden realizar sobre los juegos
 * @autor Violeta,Nacho,Denis, Alejandro
 * @version 1.0
 * @date 2024
 */
public interface EventoService {

    DtoEvento saveEvento(DtoEvento dtoEvento);

}
