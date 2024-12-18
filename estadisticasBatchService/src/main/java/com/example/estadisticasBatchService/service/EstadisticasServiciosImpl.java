package com.example.estadisticasBatchService.service;

import com.example.estadisticasBatchService.feignClients.VentaClient;
import org.springframework.beans.factory.annotation.Autowired;

public class EstadisticasServiciosImpl implements EstadisticasService {
    @Autowired
    private VentaClient ventaClient;

    @Autowired


    @Override
    public double calcularMediaPrecios(String fecha) {
        return 0;
    }

    @Override
    public void guardarEstadistica(String fecha) {

    }
}
