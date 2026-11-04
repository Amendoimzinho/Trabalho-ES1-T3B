package com.trabalho_es_3b.trabalhot3b.model;

public class Logradouro {
    private TipoLogradouro tipoLogradouro;
    private String nome;

    public Logradouro() {}
    public Logradouro(TipoLogradouro tipoLogradouro, String nome) {
        this.tipoLogradouro = tipoLogradouro;
        this.nome = nome;
    }

    public TipoLogradouro getTipoLogradouro() {
        return tipoLogradouro;
    }

    public void setTipoLogradouro(TipoLogradouro tipoLogradouro) {
        this.tipoLogradouro = tipoLogradouro;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
