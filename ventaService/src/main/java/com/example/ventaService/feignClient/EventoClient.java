package com.example.ventaService.feignClient;

import com.example.ventaService.model.Evento;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "eventoService",url = "localhost:8888/evento")//PONEMOS EL NOMBRE DEL MICROSERVICIO (EL QUE PONEMOS EN EL PROPERTIES)
public interface EventoClient {
    @GetMapping("/details/{id}")
    ResponseEntity<?> getDetalles(@PathVariable Long id);

    @GetMapping("/{id}")
    Evento getEventoById(@PathVariable("id") Long idEvento);
}
