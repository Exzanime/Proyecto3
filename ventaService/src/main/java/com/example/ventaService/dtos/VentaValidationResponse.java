package com.example.ventaService.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class VentaValidationResponse {
    @JsonProperty("timestamp")
    private String timestamp;
    @JsonProperty("status")
    private String status;
    @JsonProperty("error")
    private String error;
    @JsonProperty("message")
    private List<String> message;
    @JsonProperty("info")
    private VentaValidationRequest info;
    @JsonProperty("infoadicional")
    private String infoadicional;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<String> getMessage() {
        return message;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }

    public VentaValidationRequest getInfo() {
        return info;
    }

    public void setInfo(VentaValidationRequest info) {
        this.info = info;
    }

    public String getInfoadicional() {
        return infoadicional;
    }

    public void setInfoadicional(String infoadicional) {
        this.infoadicional = infoadicional;
    }
}
