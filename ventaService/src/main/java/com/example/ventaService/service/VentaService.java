package com.example.ventaService.service;

import com.example.ventaService.dtos.*;
import com.example.ventaService.model.VentaEntity;
import java.util.List;

public interface VentaService {

    VentaEntity ventaEntradas(VentaRequest ventaRequest);

    List<DtoVenta> getVentasByUserEmail(String userEmail);

    List<ResponseMessage> validateVenta(VentaRequest ventaRequest);
    public void isValidateEmail(String email);

    List<DtoVenta> getVentasByFecha(String fecha);
}
