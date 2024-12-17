package com.example.ventaService.feignClient;

import com.example.ventaService.dtos.UserValidationRequest;
import com.example.ventaService.dtos.UserValidationResponse;
import com.example.ventaService.dtos.VentaValidationRequest;
import com.example.ventaService.dtos.VentaValidationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "LucaBanking", url = "http://banco.eu-west-3.elasticbeanstalk.com")
public interface BancoClient {
    @PostMapping(value = "/pasarela/validaruser")
    UserValidationResponse validarUsuario(@RequestParam("user") String user, @RequestParam("password") String password);
    @PostMapping(value = "/pasarela/validacion")
    VentaValidationResponse validarVenta(@RequestBody VentaValidationRequest request, @RequestHeader("Authorization") String jwtToken);
}
