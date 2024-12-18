package com.example.ventaService.service;

import com.example.ventaService.dtos.*;
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

@Service
public class VentaServiceImpl implements VentaService{
    @Autowired
    private VentaRepository ventaRepository;
    @Autowired
    private EventoClient eventoClient;
    @Autowired
    private UsuarioClient usuarioClient;
    @Autowired
    private BancoClient bancoClient;

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
                    .date(LocalDateTime.now())
                    .build());
            return errores;
        }
        if(dtoVenta.getFechaCompra()==null && dtoVenta.getNombreEvento()=="" && dtoVenta.getUserEmail()=="" && dtoVenta.getPrecio()==0){
            errores.add(ResponseMessage.builder()
                    .message("Usuario no puede ser vacío")
                    .cause("Se ha proporcionado un usuario vacío")
                    .status(HttpStatus.BAD_REQUEST)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .date(LocalDateTime.now())
                    .build());
            return errores;
        }
        if(dtoVenta.getNombreEvento()==null){
            errores.add(ResponseMessage.builder()
                    .message("El nombre del evento no puede ser nulo")
                    .cause("No se ha proporcionado un nombre")
                    .status(HttpStatus.BAD_REQUEST)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .date(LocalDateTime.now())
                    .build());
        }
        if(dtoVenta.getNombreEvento()==""){
            errores.add(ResponseMessage.builder()
                    .message("El nombre del evento no puede ser vacío")
                    .cause("Se ha proporcionado un nombre vacío")
                    .status(HttpStatus.BAD_REQUEST)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .date(LocalDateTime.now())
                    .build());
        }
        if(dtoVenta.getUserEmail()==""){
            errores.add(ResponseMessage.builder()
                    .message("El eMail del usuario no puede ser vacío")
                    .cause("Se ha proporcionado un email vacío")
                    .status(HttpStatus.BAD_REQUEST)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .date(LocalDateTime.now())
                    .build());
        }
        if(dtoVenta.getUserEmail()==null){
            errores.add(ResponseMessage.builder()
                    .message("El eMail del usuario no puede ser nulo")
                    .cause("No se ha proporcionado un eMail")
                    .status(HttpStatus.BAD_REQUEST)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .date(LocalDateTime.now())
                    .build());
        }

        if(dtoVenta.getFechaCompra()==null){
            errores.add(ResponseMessage.builder()
                    .message("La fecha de la compra no puede ser nulo")
                    .cause("No se ha proporcionado una fecha")
                    .status(HttpStatus.BAD_REQUEST)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .date(LocalDateTime.now())
                    .build());
        }
        if(dtoVenta.getPrecio()==0){
            errores.add(ResponseMessage.builder()
                    .message("El precio no puede estar vacio")
                    .cause("No se ha proporcionado un precio")
                    .status(HttpStatus.BAD_REQUEST)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .date(LocalDateTime.now())
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

    public VentaEntity ventaEntradas(String emailUsuario, Long idEvento, DtoTarjeta tarjeta) {
        UserValidationResponse usuarioValidado = bancoClient.validarUsuario("Grupo04", "AntoniosRules");
        if(usuarioValidado == null || usuarioValidado.getToken() == null){
            throw new RuntimeException("Usuario no válido o no autorizado");
        }

        VentaValidationResponse response = bancoClient.validarVenta(VentaValidationRequest.builder()
                .nombreTitular(tarjeta.getNombreTitular())
                .numeroTarjeta(tarjeta.getNumero())
                .cvv(tarjeta.getCvv())
                .mesCaducidad(tarjeta.getMesCaducidad())
                .yearCaducidad(tarjeta.getYearCaducidad())
                .emisor(usuarioValidado.getUser())
                .concepto("Compra de entradas")
                .cantidad("100")
                .build(), usuarioValidado.getToken());
        if(response == null){
            throw new RuntimeException("Tarjeta no válida");
        }
        Evento evento = eventoClient.getEventoById(idEvento);
        if(evento == null){
            throw new RuntimeException("Evento no encontrado");
        }
        if(!validarTarjeta(tarjeta)){
            throw new RuntimeException("Tarjeta no encontrada");
        }

        VentaEntity venta = new VentaEntity();
        venta.setUsuarioId(1L);
        venta.setUserEmail(emailUsuario);
        venta.setEventoId(evento.getId());
        venta.setNombreEvento(evento.getNombre());
        venta.setFechaCompra(LocalDateTime.now());
        venta.setPrecio(evento.getPrecioMin());

        return ventaRepository.save(venta);
    }

    @Override
    public List<DtoVenta> getVentasByUserEmail(String userEmail) {
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
                    .date(LocalDateTime.now())
                    .build());
            return errores;
        }
        if(ventaRequest.getNombreTitular()==null){
            errores.add(ResponseMessage.builder()
                    .message("El nombre del titular no puede ser nulo")
                    .cause("No se ha proporcionado un nombre")
                    .status(HttpStatus.BAD_REQUEST)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .date(LocalDateTime.now())
                    .build());
        }
        if(ventaRequest.getNombreTitular()==""){
            errores.add(ResponseMessage.builder()
                    .message("El nombre del titular no puede ser vacío")
                    .cause("Se ha proporcionado un nombre vacío")
                    .status(HttpStatus.BAD_REQUEST)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .date(LocalDateTime.now())
                    .build());
        }
        if(ventaRequest.getNumero()==""){
            errores.add(ResponseMessage.builder()
                    .message("El número de la tarjeta no puede ser vacío")
                    .cause("Se ha proporcionado un número vacío")
                    .status(HttpStatus.BAD_REQUEST)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .date(LocalDateTime.now())
                    .build());
        }
        if(ventaRequest.getNumero()==null){
            errores.add(ResponseMessage.builder()
                    .message("El número de la tarjeta no puede ser nulo")
                    .cause("No se ha proporcionado un número")
                    .status(HttpStatus.BAD_REQUEST)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .date(LocalDateTime.now())
                    .build());
        }

        if(ventaRequest.getMesCaducidad()==null) {
            errores.add(ResponseMessage.builder()
                    .message("El mes de caducidad no puede ser nulo")
                    .cause("No se ha proporcionado un mes de caducidad")
                    .status(HttpStatus.BAD_REQUEST)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .date(LocalDateTime.now())
                    .build());
        }
        if(ventaRequest.getYearCaducidad()==null) {
            errores.add(ResponseMessage.builder()
                    .message("El año de caducidad no puede ser nulo")
                    .cause("No se ha proporcionado un año de caducidad")
                    .status(HttpStatus.BAD_REQUEST)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .date(LocalDateTime.now())
                    .build());
        }
        if(ventaRequest.getCvv()==null) {
            errores.add(ResponseMessage.builder()
                    .message("El cvv no puede ser nulo")
                    .cause("No se ha proporcionado un cvv")
                    .status(HttpStatus.BAD_REQUEST)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .date(LocalDateTime.now())
                    .build());
        }
        return errores;
    }

    private boolean validarTarjeta(DtoTarjeta tarjeta){
        return tarjeta != null &&
                tarjeta.getNumero() != null &&
                tarjeta.getCvv() != null &&
                tarjeta.getMesCaducidad() != null
                && tarjeta.getYearCaducidad() != null;

    }

}
