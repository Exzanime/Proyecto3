package com.example.ventaService.feignClient;


import com.example.ventaService.dtos.UserValidationRequest;
import com.example.ventaService.dtos.UserValidationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "usuarioService",url = "localhost:7777/usuario")
public interface UsuarioClient {

    @GetMapping("/validar")
    UserValidationResponse validarUsuario(@RequestBody UserValidationRequest request);
}
