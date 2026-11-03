package com.trabalho_es_3b.trabalhot3b.model;

public class TipoLogradouro {
    private int idTipo;
    private String nome;

    public TipoLogradouro(int idTipo, String nome) {
        this.idTipo = idTipo;
        this.nome = nome;
    }

    public int getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
