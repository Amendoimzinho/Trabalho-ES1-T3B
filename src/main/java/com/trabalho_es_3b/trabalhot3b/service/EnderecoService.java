package com.trabalho_es_3b.trabalhot3b.service;

import com.trabalho_es_3b.trabalhot3b.model.*;
import com.trabalho_es_3b.trabalhot3b.model.dto.ViaCepDTO;
import com.trabalho_es_3b.trabalhot3b.util.Utils;
import org.springframework.stereotype.Service;


import com.trabalho_es_3b.trabalhot3b.dao.EnderecoDAO;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class EnderecoService {
    private final EnderecoDAO enderecoDAO;

    public EnderecoService (EnderecoDAO enderecoDAO) {
        this.enderecoDAO = enderecoDAO;
    }

    public Endereco cadastrarEndereco(Endereco endereco) {

        if (endereco == null || endereco.getCEP() == null ||
                endereco.getCidade() == null || endereco.getCidade().getUnidadeFederacao() == null ||
                endereco.getBairro() == null ||
                endereco.getLogradouro() == null || endereco.getLogradouro().getTipoLogradouro() == null) {

            return null;
        }

        if (endereco.getCidade().getNome().isBlank() ||
                endereco.getCidade().getUnidadeFederacao().getSigla().isBlank() ||
                endereco.getBairro().getNome().isBlank() ||
                endereco.getLogradouro().getNome().isBlank() ||
                endereco.getLogradouro().getTipoLogradouro().getNome().isBlank()) {

            return null;
        }

        if (!Utils.isCepValido(endereco.getCEP())) {
            return null;
        }

        endereco.setCEP(Utils.formatarCep(endereco.getCEP()));


        endereco.getCidade().getUnidadeFederacao().setSigla(
                Utils.formatarNome(endereco.getCidade().getUnidadeFederacao().getSigla())
        );
        if (endereco.getCidade().getUnidadeFederacao().getNome() != null) {
            endereco.getCidade().getUnidadeFederacao().setNome(
                    Utils.formatarNome(endereco.getCidade().getUnidadeFederacao().getNome())
            );
        }

        // Cidade
        endereco.getCidade().setNome(
                Utils.formatarNome(endereco.getCidade().getNome())
        );

        // Bairro
        endereco.getBairro().setNome(
                Utils.formatarNome(endereco.getBairro().getNome())
        );

        // Logradouro e Tipo
        endereco.getLogradouro().setNome(
                Utils.formatarNome(endereco.getLogradouro().getNome())
        );

        // 4. Salva no banco de dados
        return enderecoDAO.salvarEndereco(endereco);
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

        return enderecoDAO.obterEnderecoPorId(id);
    }

    public Endereco obterEnderecoExterno(String cep) {

        if (!Utils.isCepValido(cep)) {
            return null;
        }

        String url = "https://viacep.com.br/ws/" + Utils.formatarCep(cep) + "/json/";

        try {
            RestTemplate restTemplate = new RestTemplate();
            ViaCepDTO dto = restTemplate.getForObject(url, ViaCepDTO.class);

            if (dto == null || (dto.getErro() != null && dto.getErro())) {
                return null;
            }

            String[] logradouroCompleto = Utils.separarLogradouro(Utils.formatarNome(dto.getLogradouro()));

            String tipoLogradouro;
            String nomeRua;
            if (logradouroCompleto == null) {
                tipoLogradouro = "NAO INFORMADO";
                nomeRua = "NAO INFORMADO";
            }
            else {
                tipoLogradouro = logradouroCompleto[0];
                nomeRua = logradouroCompleto[1];
            }

            UnidadeFederacao estado = new UnidadeFederacao(Utils.formatarNome(dto.getUf()), Utils.formatarNome(dto.getEstado()));
            Cidade cidade = new Cidade(Utils.formatarNome(dto.getLocalidade()), estado);
            Bairro bairro = new Bairro(Utils.formatarNome(dto.getBairro()));

            TipoLogradouro tipo = new TipoLogradouro(tipoLogradouro);
            Logradouro rua = new Logradouro(tipo, nomeRua);

            return new Endereco(cidade, bairro, rua, cep);

        } catch (Exception e) {
            return null;
        }
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
