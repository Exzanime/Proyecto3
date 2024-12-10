package com.example.eventoService.service;

import com.example.eventoService.controller.error.EventoDuplicadoExcp;
import com.example.eventoService.dto.DtoEvento;
import com.example.eventoService.entity.Evento;
import com.example.eventoService.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;


/**
 * Implementacion de la interfaz EventoService
 * @autor Violeta,Nacho,Denis, Alejandro
 * @version 1.0
 * @date 2024
 */
@Service
public class EventoServiceImpl implements EventoService{
    @Autowired
    EventoRepository eventoRepository;

    /**
     * @param dtoEvento
     * @return
     */
    @Override
    public DtoEvento saveEvento(DtoEvento dtoEvento) {

        Optional<Evento> e = eventoRepository.findByNombreAndGeneroAndFecha(dtoEvento.getNombre(),dtoEvento.getGenero(),dtoEvento.getFecha());
        if (e != null){
            throw new EventoDuplicadoExcp("Error al crear evento", "El evento "+dtoEvento.getNombre()+" ya existe", HttpStatus.CONFLICT);
        }
        Evento evento=Evento.builder()
                .fecha(dtoEvento.getFecha())
                .nombre(dtoEvento.getNombre())
                .genero(dtoEvento.getGenero())
                .descripcion(dtoEvento.getDescripcion())
                .recinto(dtoEvento.getRecinto())
                .localidad(dtoEvento.getLocalidad())
                .precioMin(dtoEvento.getPrecioMin())
                .precioMax(dtoEvento.getPrecioMax())
                .build();
        eventoRepository.save(evento);
        return dtoEvento;
    }

    @Override
    public DtoEvento getDetalleEvento(Long id) {
        Evento e = eventoRepository.findById(id).orElse(null);
        if(e!=null){
            return DtoEvento.builder()
                    .fecha(e.getFecha())
                    .nombre(e.getNombre())
                    .genero(e.getGenero())
                    .descripcion(e.getDescripcion())
                    .recinto(e.getRecinto())
                    .localidad(e.getLocalidad())
                    .precioMin(e.getPrecioMin())
                    .precioMax(e.getPrecioMax())
                    .build();
        }
        return null;
    }
}
