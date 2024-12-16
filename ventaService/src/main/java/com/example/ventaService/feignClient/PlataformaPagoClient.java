package com.example.ventaService.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "plataformaPago")
@RequestMapping("/pago")
public interface PlataformaPagoClient {
}
