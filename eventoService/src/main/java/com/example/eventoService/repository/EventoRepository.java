package com.example.eventoService.repository;

import com.example.eventoService.entity.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Repositorio de la entidad Evento
 * @autor Violeta,Nacho,Denis, Alejandro
 * @version 1.0
 * @date 2024
 */
@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {

    Optional<Evento> findByNombreAndGeneroAndFecha(String nombre, String genero, LocalDateTime fecha);
}
