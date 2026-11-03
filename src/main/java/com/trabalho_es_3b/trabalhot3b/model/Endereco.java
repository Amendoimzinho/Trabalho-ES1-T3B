package com.trabalho_es_3b.trabalhot3b.model;

public class Endereco {
    private org.example.models.endereco.Cidade cidade;
    private org.example.models.endereco.Bairro bairro;
    private Logradouro logradouro;

    private String CEP;

    public Endereco(org.example.models.endereco.Cidade cidade, org.example.models.endereco.Bairro bairro, Logradouro logradouro, String CEP) {
        this.cidade = cidade;
        this.bairro = bairro;
        this.logradouro = logradouro;
        this.CEP = CEP;
    }

    public org.example.models.endereco.Cidade getCidade() {
        return cidade;
    }

    public void setCidade(org.example.models.endereco.Cidade cidade) {
        this.cidade = cidade;
    }

    public org.example.models.endereco.Bairro getBairro() {
        return bairro;
    }

    public void setBairro(org.example.models.endereco.Bairro bairro) {
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
