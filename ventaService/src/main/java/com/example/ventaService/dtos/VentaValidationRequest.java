package com.example.ventaService.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;


public class VentaValidationRequest {
    @JsonProperty("numeroTarjeta")
    String numeroTarjeta;
    @JsonProperty("mesCaducidad")
    String mesCaducidad;
    @JsonProperty("yearCaducidad")
    String yearCaducidad;
    @JsonProperty("cvv")
    String cvv;
    @JsonProperty("nombreTitular")
    String nombreTitular;
    @JsonProperty("emisor")
    String emisor;
    @JsonProperty("concepto")
    String concepto;
    @JsonProperty("cantidad")
    String cantidad;

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getMesCaducidad() {
        return mesCaducidad;
    }

    public void setMesCaducidad(String mesCaducidad) {
        this.mesCaducidad = mesCaducidad;
    }

    public String getYearCaducidad() {
        return yearCaducidad;
    }

    public void setYearCaducidad(String yearCaducidad) {
        this.yearCaducidad = yearCaducidad;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getNombreTitular() {
        return nombreTitular;
    }

    public void setNombreTitular(String nombreTitular) {
        this.nombreTitular = nombreTitular;
    }

    public String getEmisor() {
        return emisor;
    }

    public void setEmisor(String emisor) {
        this.emisor = emisor;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public VentaValidationRequest() {
    }

    public VentaValidationRequest(String numeroTarjeta, String mesCaducidad, String yearCaducidad, String cvv, String nombreTitular, String emisor, String concepto, String cantidad) {
        this.numeroTarjeta = numeroTarjeta;
        this.mesCaducidad = mesCaducidad;
        this.yearCaducidad = yearCaducidad;
        this.cvv = cvv;
        this.nombreTitular = nombreTitular;
        this.emisor = emisor;
        this.concepto = concepto;
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "VentaValidationRequest{" +
                "numeroTarjeta='" + numeroTarjeta + '\'' +
                ", mesCaducidad='" + mesCaducidad + '\'' +
                ", yearCaducidad='" + yearCaducidad + '\'' +
                ", cvv='" + cvv + '\'' +
                ", nombreTitular='" + nombreTitular + '\'' +
                ", emisor='" + emisor + '\'' +
                ", concepto='" + concepto + '\'' +
                ", cantidad='" + cantidad + '\'' +
                '}';
    }
}
