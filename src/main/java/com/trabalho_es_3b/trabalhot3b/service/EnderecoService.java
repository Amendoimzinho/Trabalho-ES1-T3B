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
                endereco.getCidade().getUnidadeFederacao().getNome() == null ||
                endereco.getCidade().getUnidadeFederacao().getNome().isBlank() ||
                endereco.getBairro().getNome().isBlank() ||
                endereco.getLogradouro().getNome().isBlank() ||
                endereco.getLogradouro().getTipoLogradouro().getNome().isBlank()) {
            return null;
        }

        if (!Utils.isCepValido(endereco.getCEP())) {
            return null;
        }

        endereco.setCEP(Utils.formatarCep(endereco.getCEP()));

        String nomeEstadoFormatado = Utils.formatarNome(endereco.getCidade().getUnidadeFederacao().getNome());
        String nomeTipoFormatado = Utils.formatarNome(endereco.getLogradouro().getTipoLogradouro().getNome());

        UnidadeFederacao estadoExistente = obterUnidadeFederacaoPorNome(nomeEstadoFormatado);
        TipoLogradouro tipoExistente = obterTipoLogradouroPorNome(nomeTipoFormatado);

        if (estadoExistente == null || tipoExistente == null) {
            return null;
        }

        endereco.getCidade().setUnidadeFederacao(estadoExistente);
        endereco.getLogradouro().setTipoLogradouro(tipoExistente);

        endereco.getCidade().setNome(Utils.formatarNome(endereco.getCidade().getNome()));
        endereco.getBairro().setNome(Utils.formatarNome(endereco.getBairro().getNome()));
        endereco.getLogradouro().setNome(Utils.formatarNome(endereco.getLogradouro().getNome()));

        return enderecoDAO.cadastrarEndereco(endereco);
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

    public List<Cidade> obterCidadePorNome(String nome) {
        if (nome == null || nome.isBlank()) {
            return null;
        }

        return enderecoDAO.obterCidadesPorNome(Utils.formatarNome(nome));
    }

    public UnidadeFederacao obterUnidadeFederacaoPorNome(String nome) {
        if (nome == null || nome.isBlank()) {
            return null;
        }

        return enderecoDAO.obterUnidadeFederacaoPorNome(Utils.formatarNome(nome));
    }

    public TipoLogradouro obterTipoLogradouroPorNome(String nome) {
        if (nome == null || nome.isBlank()) {
            return null;
        }

        return enderecoDAO.obterTipoLogradouroPorNome(Utils.formatarNome(nome));
    }
}
