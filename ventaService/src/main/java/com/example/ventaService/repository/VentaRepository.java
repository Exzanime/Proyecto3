package com.example.ventaService.repository;

import com.example.ventaService.model.VentaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VentaRepository extends JpaRepository<VentaEntity, Long> {
    @Query("SELECT v FROM VentaEntity v WHERE v.userEmail = ?1")
    List<VentaEntity> findVentasByUserEmail(String userEmail);
}