package com.trabalho_es_3b.trabalhot3b.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.trabalho_es_3b.trabalhot3b.service.EnderecoService;
import com.trabalho_es_3b.trabalhot3b.model.Cidade;
import com.trabalho_es_3b.trabalhot3b.model.Endereco;

import java.util.List;

@RestController
@RequestMapping("/api/enderecos")
public class EnderecoController {
    private final EnderecoService enderecoService;

    public EnderecoController (EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    @PostMapping
    public ResponseEntity<Endereco> cadastrarEndereco(@RequestBody Endereco endereco) {
        Endereco enderecoSalvo = enderecoService.cadastrarEndereco(endereco);

        return ResponseEntity.ok(enderecoSalvo);
    }

    @GetMapping("/busca")
    public ResponseEntity<List<Endereco>> obterEnderecoPorCEP(@RequestParam String cep) {
        List<Endereco> enderecosPorCep = enderecoService.obterEnderecoPorCEP(cep);

        if (enderecosPorCep == null || enderecosPorCep.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(enderecosPorCep);
    }

    @GetMapping("/busca")
    public ResponseEntity<Endereco> obterEnderecoPorID(@RequestParam Integer id) {
        Endereco endereco = enderecoService.obterEnderecoPorID(id);

        if (endereco == null)
            return  ResponseEntity.notFound().build();

        return ResponseEntity.ok(endereco);
    }

    @GetMapping("/buscarcep")
    public ResponseEntity<Endereco> obterEnderecoExterno(@RequestParam String cep) {
        Endereco endereco = enderecoService.obterEnderecoExterno(cep);

        if (endereco == null)
            return  ResponseEntity.notFound().build();

        return ResponseEntity.ok(endereco);
    }

    @GetMapping("/cidade")
    public ResponseEntity<Cidade> obterCidade(
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam(value = "nome", required = false) String nome) {

        if (id == null && nome == null)
            return ResponseEntity.badRequest().build();

        Cidade cidade = null;

        if (id != null) {
            cidade = enderecoService.obterCidadePorId(id);
        } else {
            cidade = enderecoService.obterCidadePorNome(nome);
        }

        if (cidade == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(cidade);
    }
}
