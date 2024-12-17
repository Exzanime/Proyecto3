package com.example.ventaService.feignClient;

import com.example.ventaService.dtos.UserValidationRequest;
import com.example.ventaService.dtos.UserValidationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "LucaBanking", url = "http://banco.eu-west-3.elasticbeanstalk.com")
public interface BancoClient {
    @PostMapping(value = "/pasarela/validaruser")
    UserValidationResponse validarUsuario(@RequestParam("user") String user, @RequestParam("password") String password);
}
