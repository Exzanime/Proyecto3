package com.example.ventaService.service;

import com.example.ventaService.dtos.DtoVenta;
import com.example.ventaService.dtos.ResponseMessage;

import java.util.List;

public interface VentaService {

    DtoVenta saveVenta (DtoVenta dtoVenta);

    public List<ResponseMessage> validatePost(DtoVenta dtoVenta);
}
