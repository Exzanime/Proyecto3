package com.example.ventaService.service;

import com.example.ventaService.dtos.*;
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

@Service
public class VentaServiceImpl implements VentaService{
    @Autowired
    private VentaRepository ventaRepository;
    @Autowired
    private EventoClient eventoClient;
    @Autowired
    private UsuarioClient usuarioClient;

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

    /**
     * @param dtoVenta
     * @return
     */
    @Override
    public List<ResponseMessage> validatePost(DtoVenta dtoVenta) {
        List<ResponseMessage> errores = new ArrayList<>();

        if(dtoVenta == null){
            errores.add(ResponseMessage.builder()
                    .message("La venta no puede ser null")
                    .cause("Se ha proporcionado una venta null")
                    .status(HttpStatus.BAD_REQUEST)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                    .build());
            return errores;
        }
        if(dtoVenta.getFechaCompra()==null && dtoVenta.getNombreEvento()=="" && dtoVenta.getUserEmail()=="" && dtoVenta.getPrecio()==null){
            errores.add(ResponseMessage.builder()
                    .message("La venta no puede estar vacia")
                    .cause("Se ha proporcionado un usuario vacío")
                    .status(HttpStatus.BAD_REQUEST)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                    .build());
            return errores;
        }
        if(dtoVenta.getNombreEvento()==null){
            errores.add(ResponseMessage.builder()
                    .message("El nombre del evento no puede ser nulo")
                    .cause("No se ha proporcionado un nombre")
                    .status(HttpStatus.BAD_REQUEST)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                    .build());
        }
        if(dtoVenta.getNombreEvento()==""){
            errores.add(ResponseMessage.builder()
                    .message("El nombre del evento no puede ser vacío")
                    .cause("Se ha proporcionado un nombre vacío")
                    .status(HttpStatus.BAD_REQUEST)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                    .build());
        }
        if(dtoVenta.getUserEmail()==""){
            errores.add(ResponseMessage.builder()
                    .message("El eMail del usuario no puede ser vacío")
                    .cause("Se ha proporcionado un email vacío")
                    .status(HttpStatus.BAD_REQUEST)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                    .build());
        }
        if(dtoVenta.getUserEmail()==null){
            errores.add(ResponseMessage.builder()
                    .message("El eMail del usuario no puede ser nulo")
                    .cause("No se ha proporcionado un eMail")
                    .status(HttpStatus.BAD_REQUEST)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                    .build());
        }

        if(dtoVenta.getFechaCompra()==null){
            errores.add(ResponseMessage.builder()
                    .message("La fecha de la compra no puede ser nulo")
                    .cause("No se ha proporcionado una fecha")
                    .status(HttpStatus.BAD_REQUEST)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                    .build());
        }
        if(dtoVenta.getPrecio()==null){
            errores.add(ResponseMessage.builder()
                    .message("El precio no puede estar vacio")
                    .cause("No se ha proporcionado un precio")
                    .status(HttpStatus.BAD_REQUEST)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                    .build());
        }


        return errores;
    }


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
                tarjeta.getFechaExpiracion() != null;

    }

}
