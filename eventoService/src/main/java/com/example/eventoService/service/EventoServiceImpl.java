package com.example.eventoService.service;

import com.example.eventoService.dto.DtoEvento;
import com.example.eventoService.dto.ResponseMessage;
import com.example.eventoService.entity.Evento;
import com.example.eventoService.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

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
     * Elimina un evento almacenado en la capa repositorio encontrándolo
     * por su identificador
     * @param id Long autoincremental que se genera al crear los eventos
     */
    @Override
    @Transactional
    public void deleteById(Long id) {
        eventoRepository.deleteById(id);
    }

    @Override
    public List<ResponseMessage> validate(DtoEvento dtoEvento) {
        List<ResponseMessage> errores = new ArrayList<>();
        if(dtoEvento == null){
            errores.add(ResponseMessage.builder()
                    .message("Evento no puede ser null")
                    .cause("Se ha proporcionado un evento null")
                    .status(HttpStatus.BAD_REQUEST)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .date(LocalDateTime.now().format(new DateTimeFormatterBuilder().appendPattern("dd/MM/yyyy").toFormatter()))
                    .build());
            return errores;
        }
        if(dtoEvento.getGenero()==""&&dtoEvento.getNombre()==""&&dtoEvento.getFecha()==""&&dtoEvento.getLocalidad()==""&&dtoEvento.getRecinto()==""&&dtoEvento.getDescripcion()==""&&dtoEvento.getPrecioMin()==0.0&&dtoEvento.getPrecioMax()==0.0){
            errores.add(ResponseMessage.builder()
                    .message("Evento no puede estar vacio")
                    .cause("Se ha proporcionado un evento vacio")
                    .status(HttpStatus.BAD_REQUEST)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .date(LocalDateTime.now().format(new DateTimeFormatterBuilder().appendPattern("dd/MM/yyyy").toFormatter()))
                    .build());
            return errores;
        }
        if (dtoEvento.getGenero()==null) {
            errores.add(ResponseMessage.builder()
                    .message("Genero no puede ser nulo")
                    .cause("No se ha proporcionado un genero")
                    .status(HttpStatus.BAD_REQUEST)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .date(LocalDateTime.now().format(new DateTimeFormatterBuilder().appendPattern("dd/MM/yyyy").toFormatter()))
                    .build());
        }
        if (dtoEvento.getGenero()=="") {
            errores.add(ResponseMessage.builder()
                    .message("Genero no puede estar vacio")
                    .cause("Se ha proporcionado un genero vacio")
                    .status(HttpStatus.BAD_REQUEST)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .date(LocalDateTime.now().format(new DateTimeFormatterBuilder().appendPattern("dd/MM/yyyy").toFormatter()))
                    .build());
        }
        if (dtoEvento.getDescripcion()=="") {
            errores.add(ResponseMessage.builder()
                    .message("Descripcion no puede estar vacio")
                    .cause("Se ha proporcionado una descripcion vacia")
                    .status(HttpStatus.BAD_REQUEST)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .date(LocalDateTime.now().format(new DateTimeFormatterBuilder().appendPattern("dd/MM/yyyy").toFormatter()))
                    .build());
        }
        if (dtoEvento.getDescripcion()==null) {
            errores.add(ResponseMessage.builder()
                    .message("Descripcion no puede ser nulo")
                    .cause("No se ha proporcionado una descripcion")
                    .status(HttpStatus.BAD_REQUEST)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .date(LocalDateTime.now().format(new DateTimeFormatterBuilder().appendPattern("dd/MM/yyyy").toFormatter()))
                    .build());
        }
        if (dtoEvento.getFecha()=="") {
            errores.add(ResponseMessage.builder()
                    .message("Fecha no puede estar vacio")
                    .cause("Se ha proporcionado una fecha vacia")
                    .status(HttpStatus.BAD_REQUEST)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .date(LocalDateTime.now().format(new DateTimeFormatterBuilder().appendPattern("dd/MM/yyyy").toFormatter()))
                    .build());
        }
        if (dtoEvento.getFecha()==null) {
            errores.add(ResponseMessage.builder()
                    .message("Fecha no puede ser nulo")
                    .cause("No se ha proporcionado una fecha")
                    .status(HttpStatus.BAD_REQUEST)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .date(LocalDateTime.now().format(new DateTimeFormatterBuilder().appendPattern("dd/MM/yyyy").toFormatter()))
                    .build());
        }
        if (dtoEvento.getLocalidad()=="") {
            errores.add(ResponseMessage.builder()
                    .message("Localidad no puede estar vacio")
                    .cause("Se ha proporcionado una localidad vacia")
                    .status(HttpStatus.BAD_REQUEST)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .date(LocalDateTime.now().format(new DateTimeFormatterBuilder().appendPattern("dd/MM/yyyy").toFormatter()))
                    .build());
        }
        if (dtoEvento.getLocalidad()==null) {
            errores.add(ResponseMessage.builder()
                    .message("Localidad no puede ser nulo")
                    .cause("No se ha proporcionado una localidad")
                    .status(HttpStatus.BAD_REQUEST)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .date(LocalDateTime.now().format(new DateTimeFormatterBuilder().appendPattern("dd/MM/yyyy").toFormatter()))
                    .build());
        }
        if (dtoEvento.getNombre()=="") {
            errores.add(ResponseMessage.builder()
                    .message("Nombre no puede estar vacio")
                    .cause("Se ha proporcionado un nombre vacio")
                    .status(HttpStatus.BAD_REQUEST)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .date(LocalDateTime.now().format(new DateTimeFormatterBuilder().appendPattern("dd/MM/yyyy").toFormatter()))
                    .build());
        }
        if (dtoEvento.getNombre()==null) {
            errores.add(ResponseMessage.builder()
                    .message("Nombre no puede ser nulo")
                    .cause("No se ha proporcionado un nombre")
                    .status(HttpStatus.BAD_REQUEST)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .date(LocalDateTime.now().format(new DateTimeFormatterBuilder().appendPattern("dd/MM/yyyy").toFormatter()))
                    .build());
        }
        if (dtoEvento.getRecinto()=="") {
            errores.add(ResponseMessage.builder()
                    .message("Recinto no puede estar vacio")
                    .cause("Se ha proporcionado un recinto vacio")
                    .status(HttpStatus.BAD_REQUEST)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .date(LocalDateTime.now().format(new DateTimeFormatterBuilder().appendPattern("dd/MM/yyyy").toFormatter()))
                    .build());
        }
        if (dtoEvento.getRecinto()==null) {
            errores.add(ResponseMessage.builder()
                    .message("Recinto no puede ser nulo")
                    .cause("No se ha proporcionado un recinto")
                    .status(HttpStatus.BAD_REQUEST)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .date(LocalDateTime.now().format(new DateTimeFormatterBuilder().appendPattern("dd/MM/yyyy").toFormatter()))
                    .build());
        }
        if (dtoEvento.getPrecioMin() == 0.0) {
            errores.add(ResponseMessage.builder()
                    .message("Precio minimo no puede ser 0")
                    .cause("Se ha proporcionado un precio minimo vacio")
                    .status(HttpStatus.BAD_REQUEST)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .date(LocalDateTime.now().format(new DateTimeFormatterBuilder().appendPattern("dd/MM/yyyy").toFormatter()))
                    .build());
        }
        if (dtoEvento.getPrecioMax() == 0.0) {
            errores.add(ResponseMessage.builder()
                    .message("Precio maximo no puede ser 0")
                    .cause("Se ha proporcionado un precio maximo vacio")
                    .status(HttpStatus.BAD_REQUEST)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .date(LocalDateTime.now().format(new DateTimeFormatterBuilder().appendPattern("dd/MM/yyyy").toFormatter()))
                    .build());
        }
        if (dtoEvento.getPrecioMin() > dtoEvento.getPrecioMax()) {
            errores.add(ResponseMessage.builder()
                    .message("Precio minimo no puede ser mayor que el precio maximo")
                    .cause("Se ha proporcionado un precio minimo mayor que el precio maximo")
                    .status(HttpStatus.BAD_REQUEST)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .date(LocalDateTime.now().format(new DateTimeFormatterBuilder().appendPattern("dd/MM/yyyy").toFormatter()))
                    .build());
        }
        Optional<Evento> existingEvento = eventoRepository.findByNombreAndGeneroAndFecha(dtoEvento.getNombre(), dtoEvento.getGenero(), LocalDate.parse(dtoEvento.getFecha()));
        if (existingEvento.isPresent()) {
            errores.add(ResponseMessage.builder()
                    .message("Evento duplicado")
                    .cause("El evento "+dtoEvento.getNombre()+" ya existe")
                    .status(HttpStatus.BAD_REQUEST)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .date(LocalDateTime.now().format(new DateTimeFormatterBuilder().appendPattern("dd/MM/yyyy").toFormatter()))
                    .build());
        }
        return errores;
    }

    @Override
    public DtoEvento getDetalleEvento(Long id) {
        return eventoRepository.findById(id)
                .map(this::conversionEventoADto)
                .orElse(null);
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
        return DtoEvento.builder()
                .nombre(evento.getNombre())
                .descripcion(evento.getDescripcion())
                .genero(evento.getGenero())
                .localidad(evento.getLocalidad())
                .recinto(evento.getRecinto())
                .fecha(String.valueOf(evento.getFecha()))
                .precioMin(evento.getPrecioMin())
                .precioMax(evento.getPrecioMax())
                .build();
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
                .fecha(LocalDate.parse(dto.getFecha()))
                .precioMin(dto.getPrecioMin())
                .precioMax(dto.getPrecioMax())
                .build();
    }

}
