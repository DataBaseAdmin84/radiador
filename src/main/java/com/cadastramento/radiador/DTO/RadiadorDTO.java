package com.cadastramento.radiador.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RadiadorDTO {
    private String modelo;
    private String tipo;
    private String servicoExecutado;
    private String cliente;
    private LocalDate data;
    private BigDecimal preco;

    public RadiadorDTO() {}

    public RadiadorDTO(String modelo, String tipo, String servicoExecutado, String cliente, LocalDate data, BigDecimal preco) {
        this.modelo = modelo;
        this.tipo = tipo;
        this.servicoExecutado = servicoExecutado;
        this.cliente = cliente;
        this.data = data;
        this.preco = preco;
    }

    public String getModelo() {
        return modelo;
        }

    public void setModelo(String modelo) {
        this.modelo = modelo;

    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getServicoExecutado() {
        return servicoExecutado;
    }

    public void setServicoExecutado(String servicoExecutado) {
        this.servicoExecutado = servicoExecutado;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }
}
