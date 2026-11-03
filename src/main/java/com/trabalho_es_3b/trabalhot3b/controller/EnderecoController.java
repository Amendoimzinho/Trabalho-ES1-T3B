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
    public ResponseEntity<Cidade> obterCidadePorID(@RequestParam Integer id) {
        Cidade cidade = enderecoService.obterCidadePorId(id);

        if (cidade == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(cidade);
    }

    @GetMapping("/cidade")
    public ResponseEntity<List<Cidade>> obterCidadePorNome(@RequestParam String nome) {
        List<Cidade> cidades = enderecoService.obterCidadePorNome(nome);

        if (cidades == null || cidades.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(cidades);
    }
}
