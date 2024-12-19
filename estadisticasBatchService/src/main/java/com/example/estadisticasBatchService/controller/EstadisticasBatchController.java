package com.example.estadisticasBatchService.controller;

import com.example.estadisticasBatchService.repository.EstadisticaVentaRepository;
import com.example.estadisticasBatchService.service.EstadisticasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EstadisticasBatchController {
    @Autowired
    private EstadisticasService estadisticasService;
    private EstadisticaVentaRepository estadisticaVentaRepository;

    @GetMapping("/procesar-estadisticas")
    public String procesarEstadisticas(@RequestParam String fecha) {
        estadisticasService.guardarEstadistica(fecha);
        return "Proceso de estadísticas iniciado para fecha " + fecha;
    }

//    @GetMapping("/verificar-estadisticas")
//    public String verificarEstadisticas(@RequestParam String fecha) {
//        // Verificar si hay estadísticas para la fecha dada
//        if (estadisticaVentaRepository.existsByFecha(fecha)) {
//            return "Estadísticas encontradas para la fecha " + fecha;
//        } else {
//            return "No se encontraron estadísticas para la fecha " + fecha;
//        }
//    }
}

// Para simular el proceso en Postman