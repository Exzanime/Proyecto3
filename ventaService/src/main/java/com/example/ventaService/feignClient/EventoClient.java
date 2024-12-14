package com.example.ventaService.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "eventoService")//PONEMOS EL NOMBRE DEL MICROSERVICIO (EL QUE PONEMOS EN EL PROPERTIES)
@RequestMapping("/evento")//AQUI VA EL ENDPONT DEL RESTCONTROLLER DEL MICROSERVICIO
public interface EventoClient {
    @GetMapping("/details/{id}")
    ResponseEntity<?> getDetalles(@PathVariable Long id);
}
