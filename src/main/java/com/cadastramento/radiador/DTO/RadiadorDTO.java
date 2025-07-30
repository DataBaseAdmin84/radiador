package com.cadastramento.radiador.DTO;

import java.math.BigDecimal;

import java.math.BigDecimal;

public class RadiadorDTO {
    private String nome;
    private BigDecimal valor;

    // Construtores
    public RadiadorDTO() {}

    public RadiadorDTO(String nome, BigDecimal valor) {
        this.nome = nome;
        this.valor = valor;
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

}
