package com.example.ventaService.service;


import com.example.ventaService.dtos.DtoTarjeta;
import com.example.ventaService.dtos.UserValidationRequest;
import com.example.ventaService.dtos.UserValidationResponse;
import com.example.ventaService.feignClient.EventoClient;
import com.example.ventaService.feignClient.UsuarioClient;
import com.example.ventaService.model.Evento;
import com.example.ventaService.model.VentaEntity;
import com.example.ventaService.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class VentaService {
    @Autowired
    private UsuarioClient usuarioClient;
    @Autowired
    private EventoClient eventoClient;
    @Autowired
    private VentaRepository ventaRepository;

    public VentaEntity ventaEntradas(String emailUsuario, Long idEvento, DtoTarjeta tarjeta){
        UserValidationResponse usuarioValidado = usuarioClient.validarUsuario(new UserValidationRequest(emailUsuario, tarjeta.getCvv()));
        if(usuarioValidado == null || usuarioValidado.getToken() == null){
            throw new RuntimeException("Usuario no válido o no autorizado");
        }

        Evento evento = eventoClient.getEventoById(idEvento);
        if(evento == null){
            throw new RuntimeException("Evento no encontrado");
        }
        if(!validarTarjeta(tarjeta)){
            throw new RuntimeException("Tarjeta no encontrada");
        }

        VentaEntity venta = new VentaEntity();
        venta.setUsuarioId(Long.parseLong(usuarioValidado.getUser()));
        venta.setEventoId(evento.getId());
        venta.setFechaCompra(LocalDateTime.now());
        venta.setPrecio(evento.getPrecioMin());

        return ventaRepository.save(venta);
    }

    private boolean validarTarjeta(DtoTarjeta tarjeta){
        return tarjeta != null &&
                tarjeta.getNumero() != null &&
                tarjeta.getCvv() != null &&
                tarjeta.getFechaCaducidad() != null;

    }

}
