package com.example.ventaService.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserValidationResponse {
    @JsonProperty("user")
    String user;
    @JsonProperty("pwd")
    String pwd;
    @JsonProperty("token")
    String token;
}
