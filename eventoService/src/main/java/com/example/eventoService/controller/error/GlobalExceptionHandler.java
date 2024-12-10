package com.example.eventoService.controller.error;

import com.example.eventoService.dto.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EventoDuplicadoExcp.class)
    public ResponseEntity<ResponseMessage<Void>> handleEventoDuplicadoExcp(EventoDuplicadoExcp ex) {
        ResponseMessage<Void> response = ResponseMessage.<Void>builder()
                .message(ex.getMessage())
                .cause(ex.getCause() != null ? ex.getCause().toString() : "Sin causa concreta")
                .status(HttpStatus.CONFLICT)
                .errorCode(HttpStatus.CONFLICT.value())
                .date(java.time.LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

}
