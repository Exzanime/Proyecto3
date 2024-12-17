package com.example.ventaService.service;

import com.example.ventaService.dtos.DtoTarjeta;
import com.example.ventaService.dtos.DtoVenta;
import com.example.ventaService.dtos.ResponseMessage;
import com.example.ventaService.model.VentaEntity;

import java.util.List;

public interface VentaService {

    DtoVenta saveVenta (DtoVenta dtoVenta);

    public List<ResponseMessage> validatePost(DtoVenta dtoVenta);

    VentaEntity ventaEntradas(String emailUsuario, Long idEvento, DtoTarjeta tarjeta);
}
