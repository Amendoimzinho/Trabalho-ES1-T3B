package com.trabalho_es_3b.trabalhot3b.model;


public class Endereco {
    private Cidade cidade;
    private Bairro bairro;
    private Logradouro logradouro;

    private String CEP;

    public Endereco(Cidade cidade, Bairro bairro, Logradouro logradouro, String CEP) {
        this.cidade = cidade;
        this.bairro = bairro;
        this.logradouro = logradouro;
        this.CEP = CEP;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public Bairro getBairro() {
        return bairro;
    }

    public void setBairro(Bairro bairro) {
        this.bairro = bairro;
    }

    public Logradouro getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(Logradouro logradouro) {
        this.logradouro = logradouro;
    }

    public String getCEP() {
        return CEP;
    }

    public void setCEP(String CEP) {
        this.CEP = CEP;
    }
}
