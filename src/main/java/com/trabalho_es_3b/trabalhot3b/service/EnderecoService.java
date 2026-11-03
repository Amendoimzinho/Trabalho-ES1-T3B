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

    public Endereco obterEnderecoExterno(String cep) {
        // 1. Validação defensiva do CEP
        if (!Utils.isCepValido(cep)) {
            return null;
        }

        String url = "https://viacep.com.br/ws/" + Utils.formatarCep(cep) + "/json/";

        try {
            RestTemplate restTemplate = new RestTemplate();
            ViaCepDTO dto = restTemplate.getForObject(url, ViaCepDTO.class);

            // 2. Se o ViaCEP responder nulo ou com erro (CEP inexistente)
            if (dto == null || (dto.getErro() != null && dto.getErro())) {
                return null;
            }

            // 3. Separação de tipo e nome da rua
            String logradouroCompleto = dto.getLogradouro();
            String tipoLogradouro = "Rua";
            String nomeRua = logradouroCompleto;

            if (logradouroCompleto != null && logradouroCompleto.contains(" ")) {
                String[] partes = logradouroCompleto.split(" ", 2);
                tipoLogradouro = partes[0];
                nomeRua = partes[1];
            }

            // 4. Monta as entidades na memória (POJOs) sem persistir
            UnidadeFederacao estado = new UnidadeFederacao(dto.getUf().toUpperCase());
            Cidade cidade = new Cidade(Utils.formatarNome(dto.getLocalidade()), estado);
            Bairro bairro = new Bairro(Utils.formatarNome(dto.getBairro()));
            TipoLogradouro tipo = new TipoLogradouro(tipoLogradouro);
            Logradouro rua = new Logradouro(tipo, nomeRua);

            // 5. Retorna o objeto Endereco pronto para o Controller
            return new Endereco(Utils.formatarCep(dto.getCep()), cidade, bairro, rua);

        } catch (Exception e) {
            // Trata timeout ou erro de conexão com o ViaCEP
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
