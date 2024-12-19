package com.example.ventaService.feignClient;


import com.example.ventaService.dtos.UserValidationRequest;
import com.example.ventaService.dtos.UserValidationResponse;
import com.example.ventaService.model.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "usuarioService",url = "localhost:7777/usuario")
public interface UsuarioClient {

    @GetMapping("/validar")
    UserValidationResponse validarUsuario(@RequestBody UserValidationRequest request);
    @PostMapping("/registro")
    ResponseEntity<?> registrarUsuario(@RequestBody Usuario dtoUsuario);
}
