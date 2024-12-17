package com.example.ventaService.feignClient;

import com.example.ventaService.response.PagoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "ventaService")
@RequestMapping("/pago")
public interface PlataformaPagoClient {

    @PostMapping("/validacion")
    PagoResponse validarPago(@RequestBody Pago pago);
}
