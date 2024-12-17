package com.example.ventaService.service;

import com.example.ventaService.dtos.DtoVenta;
import com.example.ventaService.model.VentaEntity;
import com.example.ventaService.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VentaServiceImpl implements VentaService{
    @Autowired
    private VentaRepository ventaRepository;





    /**
     * @param dtoVenta
     * @return
     */
    @Override
    public DtoVenta saveVenta(DtoVenta dtoVenta) {
        VentaEntity venta = conversionDtoAVenta(dtoVenta);
        VentaEntity saved = ventaRepository.save(venta);
        return conversionVentaADto(saved);
    }














    private VentaEntity conversionDtoAVenta(DtoVenta dto) {
        if (dto == null) {
            throw new IllegalArgumentException("El DTO no puede ser un null.");
        }
        return VentaEntity.builder()
                .usuarioClient(dto.getUsuarioClient())
                .eventoClient(dto.getEventoClient())
                .precio(dto.getPrecio())
                .fechaCompra(dto.getFechaCompra())
                .build();
    }

    private DtoVenta conversionVentaADto(VentaEntity venta) {
        if (venta == null) {
            throw new IllegalArgumentException("La entidad no puede ser un null.");
        }
        return DtoVenta.builder()
                .usuarioClient(venta.getUsuarioClient())
                .eventoClient(venta.getEventoClient())
                .precio(venta.getPrecio())
                .fechaCompra(venta.getFechaCompra())
                .build();
    }
}
