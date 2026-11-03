package com.trabalho_es_3b.trabalhot3b.model;

public class Cidade {
    private String nome;
    private UnidadeFederacao unidadeFederacao;

    public Cidade(String nome, UnidadeFederacao unidadeFederacao) {
        this.nome = nome;
        this.unidadeFederacao = unidadeFederacao;
    }

    public UnidadeFederacao getUnidadeFederacao() {
        return unidadeFederacao;
    }

    public void setUnidadeFederacao(UnidadeFederacao unidadeFederacao) {
        this.unidadeFederacao = unidadeFederacao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
