package com.trabalho_es_3b.trabalhot3b.model;

public class UnidadeFederacao {
    private String sigla;
    private String nome;

    public UnidadeFederacao(String sigla, String nome) {
        this.sigla = sigla;
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }
}
