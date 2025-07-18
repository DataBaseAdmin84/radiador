    package com.cadastramento.radiador.model;

    import jakarta.persistence.*;
    import jakarta.validation.constraints.NotNull;

    import java.lang.annotation.Native;
    import java.util.Date;
    @Entity
    @Table(name = "servicoradiadores")
    public class Servicoradiadores {


        @Id
        @Column(name = "id")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @org.jetbrains.annotations.NotNull
        @Column(name = "modelo")
        private String modelo;

        @NotNull
        @Column(name = "tipo")
        private String tipo;

        @NotNull
        @Column(name = "servicoExecutado")
        private String servicoExecutado;

        @Column(name = "cliente")
        private String cliente;

        @Column(name = "data")
        private Date data;

        @Column(name = "preco")
        private double preco;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
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
            servicoExecutado = servicoExecutado;
        }

        public String getCliente() {
            return cliente;
        }

        public void setCliente(String cliente) {
            this.cliente = cliente;
        }

        public Date getData() {
            return data;
        }

        public void setData(Date data) {
            this.data = data;
        }

        public double getPreco() {
            return preco;
        }

        public void setPreco(double preco) {
            this.preco = preco;
        }
    }
