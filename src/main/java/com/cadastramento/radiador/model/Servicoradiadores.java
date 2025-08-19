package com.cadastramento.radiador.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "servicoradiadores")
public class Servicoradiadores {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "modelo")
    private String modelo;

    @NotNull
    @Column(name = "tipo")
    private String tipo;

    @NotNull
    @Column(name = "servicoExecutado")
    private String servicoExecutado;

    @NotNull
    @Column(name = "cliente")
    private String cliente;

    @NotNull
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "data")
    private LocalDate data;

    @NotNull
    @Column(name = "preco")
    private double preco;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getServicoExecutado() { return servicoExecutado; }
    public void setServicoExecutado(String servicoExecutado) { this.servicoExecutado = servicoExecutado; }

    public String getCliente() { return cliente; }
    public void setCliente(String cliente) { this.cliente = cliente; }

    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }

    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }
}