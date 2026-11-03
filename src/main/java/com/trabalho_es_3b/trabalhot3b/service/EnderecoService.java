package com.trabalho_es_3b.trabalhot3b.service;

import com.trabalho_es_3b.trabalhot3b.model.Cidade;
import com.trabalho_es_3b.trabalhot3b.model.Endereco;
import com.trabalho_es_3b.trabalhot3b.util.Utils;
import org.springframework.stereotype.Service;


import com.trabalho_es_3b.trabalhot3b.dao.EnderecoDAO;

import java.util.List;

@Service
public class EnderecoService {
    private final EnderecoDAO enderecoDAO;

    public EnderecoService (EnderecoDAO enderecoDAO) {
        this.enderecoDAO = enderecoDAO;
    }

    public List<Endereco> obterEnderecoPorCEP(String cep) {

        if (Utils.isCepValido(cep)) {
            return enderecoDAO.obterEnderecosPorCEP(Utils.formatarCep(cep));
        }

        return null;
    }

    public Endereco obterEnderecoPorID (Integer id){
        if (id == null || id <= 0) {
            return null;
        }

        return enderecoDAO.obterEnderecosPorID(id);
    }

    public Cidade obterCidadePorId (Integer id) {
        if (id == null || id <= 0) {
            return null;
        }

        return enderecoDAO.obterCidadePorId(id);
    }

    public Cidade obterCidadePorNome(String nome) {
        if (nome == null || nome.isBlank()) {
            return null;
        }

        return enderecoDAO.obterCidadePorNome(Utils.formatarNome(nome));
    }
}
