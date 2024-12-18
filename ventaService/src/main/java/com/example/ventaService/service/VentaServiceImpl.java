package com.example.ventaService.service;

import com.example.ventaService.dtos.*;
import com.example.ventaService.errors.EmailNoValitFormatException;
import com.example.ventaService.feignClient.BancoClient;
import com.example.ventaService.feignClient.EventoClient;
import com.example.ventaService.feignClient.UsuarioClient;
import com.example.ventaService.model.Evento;
import com.example.ventaService.model.VentaEntity;
import com.example.ventaService.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de la interfaz VentaService
 * @autor Violeta,Nacho,Denis, Alejandro
 * @version 1.0
 */
@Service
public class VentaServiceImpl implements VentaService{
    @Autowired
    private VentaRepository ventaRepository;
    @Autowired
    private EventoClient eventoClient;
    @Autowired
    private BancoClient bancoClient;

    private VentaEntity conversionDtoAVenta(DtoVenta dto) {
        if (dto == null) {
            throw new IllegalArgumentException("El DTO no puede ser un null.");
        }
        return VentaEntity.builder()
                .userEmail(dto.getUserEmail())
                .nombreEvento(dto.getNombreEvento())
                .precio(dto.getPrecio())
                .fechaCompra(dto.getFechaCompra())
                .build();
    }

    private DtoVenta conversionVentaADto(VentaEntity venta) {
        if (venta == null) {
            throw new IllegalArgumentException("La entidad no puede ser un null.");
        }
        return DtoVenta.builder()
                .userEmail(venta.getUserEmail())
                .nombreEvento(venta.getNombreEvento())
                .precio(venta.getPrecio())
                .fechaCompra(venta.getFechaCompra())
                .build();
    }

    public VentaEntity ventaEntradas(VentaRequest ventaRequest){
        UserValidationResponse usuarioValidado = bancoClient.validarUsuario("Grupo04", "AntoniosRules");
        if(usuarioValidado == null || usuarioValidado.getToken() == null){
            throw new RuntimeException("Usuario no válido o no autorizado");
        }

        VentaValidationResponse response = bancoClient.validarVenta(VentaValidationRequest.builder()
                        .cantidad(String.valueOf(ventaRequest.getPrecio()))
                        .cvv(ventaRequest.getCvv())
                        .mesCaducidad(ventaRequest.getMesCaducidad())
                        .nombreTitular(ventaRequest.getNombreTitular())
                        .numeroTarjeta(ventaRequest.getNumero())
                        .yearCaducidad(ventaRequest.getYearCaducidad())
                        .build()
                , usuarioValidado.getToken());
        if(response == null){
            throw new RuntimeException("Tarjeta no válida");
        }
        Evento evento = eventoClient.getEventoById(ventaRequest.getEventoId());
        if(evento == null){
            throw new RuntimeException("Evento no encontrado");
        }

        VentaEntity venta = new VentaEntity();
        venta.setUsuarioId(1L);
        venta.setUserEmail(ventaRequest.getUserEmail());
        venta.setEventoId(evento.getId());
        venta.setNombreEvento(evento.getNombre());
        venta.setFechaCompra(LocalDateTime.now());
        venta.setPrecio(evento.getPrecioMin());

        return ventaRepository.save(venta);
    }

    @Override
    public List<DtoVenta> getVentasByUserEmail(String userEmail) {
        isValidateEmail(userEmail);
        return ventaRepository.findVentasByUserEmail(userEmail).stream()
                .map(this::conversionVentaADto)
                .toList();
    }

    @Override
    public List<ResponseMessage> validateVenta(VentaRequest ventaRequest){
        List<ResponseMessage> errores = new ArrayList<>();
        if(ventaRequest == null){
            errores.add(ResponseMessage.builder()
                    .message("La venta no puede ser null")
                    .cause("Se ha proporcionado una venta null")
                    .status(HttpStatus.BAD_REQUEST)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .body(ValidFormatForVentaRequest.builder()
                            .ventaRequest(VentaRequest.builder().build())
                            .build())
                    .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                    .build());
            return errores;
        }
        if(ventaRequest.getNombreTitular()==null){
            errores.add(ResponseMessage.builder()
                    .message("El nombre del titular no puede ser nulo")
                    .cause("No se ha proporcionado un nombre")
                    .status(HttpStatus.BAD_REQUEST)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                    .body(ValidFormatForVentaRequest.builder()
                            .ventaRequest(VentaRequest.builder().build())
                            .build())
                    .build());
        }
        if(ventaRequest.getNombreTitular()==""){
            errores.add(ResponseMessage.builder()
                    .message("El nombre del titular no puede ser vacío")
                    .cause("Se ha proporcionado un nombre vacío")
                    .status(HttpStatus.BAD_REQUEST)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                    .build());
        }
        if(ventaRequest.getNumero()==""){
            errores.add(ResponseMessage.builder()
                    .message("El número de la tarjeta no puede ser vacío")
                    .cause("Se ha proporcionado un número vacío")
                    .status(HttpStatus.BAD_REQUEST)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                    .build());
        }
        if(ventaRequest.getNumero()==null){
            errores.add(ResponseMessage.builder()
                    .message("El número de la tarjeta no puede ser nulo")
                    .cause("No se ha proporcionado un número")
                    .status(HttpStatus.BAD_REQUEST)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .body(ValidFormatForVentaRequest.builder()
                            .ventaRequest(VentaRequest.builder().build())
                            .build())
                    .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                    .build());
        }

        if(ventaRequest.getMesCaducidad()==null) {
            errores.add(ResponseMessage.builder()
                    .message("El mes de caducidad no puede ser nulo")
                    .cause("No se ha proporcionado un mes de caducidad")
                    .status(HttpStatus.BAD_REQUEST)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .body(ValidFormatForVentaRequest.builder()
                            .ventaRequest(VentaRequest.builder().build())
                            .build())
                    .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                    .build());
        }
        if(ventaRequest.getYearCaducidad()==null) {
            errores.add(ResponseMessage.builder()
                    .message("El año de caducidad no puede ser nulo")
                    .cause("No se ha proporcionado un año de caducidad")
                    .status(HttpStatus.BAD_REQUEST)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .body(ValidFormatForVentaRequest.builder()
                            .ventaRequest(VentaRequest.builder().build())
                            .build())
                    .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                    .build());
        }
        if(ventaRequest.getCvv()==null) {
            errores.add(ResponseMessage.builder()
                    .message("El cvv no puede ser nulo")
                    .cause("No se ha proporcionado un cvv")
                    .status(HttpStatus.BAD_REQUEST)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .body(ValidFormatForVentaRequest.builder()
                            .ventaRequest(VentaRequest.builder().build())
                            .build())
                    .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                    .build());
        }
        return errores;
    }
    @Override
    public void isValidateEmail(String email) {
        if (!(email.split("@").length == 2 && email.split("@")[1].split("\\.").length == 2)) {
           throw new EmailNoValitFormatException();
        }
    }
}
