package com.vhns.uploadarquivos.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;


@Entity
public class DetalhesArquivo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String tipo;
    private String data;
    private String userAgent;

    public DetalhesArquivo(){

    }

    public DetalhesArquivo(Long id, String nome, String tipo, String data) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.data = data;
    }

    public DetalhesArquivo(String nome, String tipo, String data, String userAgent) {
        this.nome = nome;
        this.tipo = tipo;
        this.data = data;
        this.userAgent = userAgent;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
}
