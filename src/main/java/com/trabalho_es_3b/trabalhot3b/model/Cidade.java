package com.trabalho_es_3b.trabalhot3b.model;

public class Cidade {
    private UnidadeFederacao unidadeFederacao;
    private String nome;

    public Cidade(UnidadeFederacao unidadeFederacao, String nome) {
        this.unidadeFederacao = unidadeFederacao;
        this.nome = nome;
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
