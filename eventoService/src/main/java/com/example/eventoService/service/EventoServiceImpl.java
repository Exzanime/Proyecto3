package com.example.eventoService.service;

import com.example.eventoService.dto.DtoEvento;
import com.example.eventoService.entity.Evento;
import com.example.eventoService.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;


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
        Evento evento = conversionDtoAEvento(dtoEvento);
        Evento eventoGuardado = eventoRepository.save(evento);
        return conversionEventoADto(eventoGuardado);
    }

    /**
     * Se comunica con la capa de repositorio para acceder a todos sus eventos,
     * los mapea con el conversor y lo lista
     * @return Lista de todos los Dto de la entidad Evento
     */
    @Override
    public List<DtoEvento> listarEventos() {
        return eventoRepository.findAll()
                .stream()
                .map(this::conversionEventoADto)
                .toList();
    }

    /**
     * Función reutilizable para convertir una entidad Evento a su forma de DTO
     * @param evento
     * @return Evento convertido a EventoDto
     */
    private DtoEvento conversionEventoADto(Evento evento) {
        if (evento == null) {
            throw new IllegalArgumentException("La entidad no puede ser un null.");
        }

        DtoEvento dtoResultante = new DtoEvento();
        dtoResultante.setNombre(evento.getNombre());
        dtoResultante.setDescripcion(evento.getDescripcion());
        dtoResultante.setGenero(evento.getGenero());
        dtoResultante.setLocalidad(evento.getLocalidad());
        dtoResultante.setRecinto(evento.getRecinto());
        dtoResultante.setFecha(evento.getFecha());
        dtoResultante.setPrecioMin(evento.getPrecioMin());
        dtoResultante.setPrecioMax(evento.getPrecioMax());
        return dtoResultante;
    }

    /**
     * Función reutilizable para convertir un EventoDto a su forma de entidad, Evento
     * @param dto
     * @return EventoDto convertido a Evento
     */
    private Evento conversionDtoAEvento(DtoEvento dto) {
        if (dto == null) {
            throw new IllegalArgumentException("El DTO no puede ser un null.");
        }
        return Evento.builder()
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .genero(dto.getGenero())
                .localidad(dto.getLocalidad())
                .recinto(dto.getRecinto())
                .fecha(dto.getFecha())
                .precioMin(dto.getPrecioMin())
                .precioMax(dto.getPrecioMax())
                .build();
    }

}
