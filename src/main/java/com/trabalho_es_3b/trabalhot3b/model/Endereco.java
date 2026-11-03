package com.trabalho_es_3b.trabalhot3b.model;


public class Endereco {
    private Integer id;
    private Cidade cidade;
    private Bairro bairro;
    private Logradouro logradouro;

    private String CEP;

    public Endereco(Cidade cidade, Bairro bairro, Logradouro logradouro, String CEP, Integer id) {
        this.cidade = cidade;
        this.bairro = bairro;
        this.logradouro = logradouro;
        this.CEP = CEP;
        this.id = id;
    }

    public Endereco(Cidade cidade, Bairro bairro, Logradouro logradouro, String CEP) {
        this.cidade = cidade;
        this.bairro = bairro;
        this.logradouro = logradouro;
        this.CEP = CEP;
        this.id = null;
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

    public void setId(Integer id) { this.id = id; }

    public Integer getId() { return this.id; }

}
