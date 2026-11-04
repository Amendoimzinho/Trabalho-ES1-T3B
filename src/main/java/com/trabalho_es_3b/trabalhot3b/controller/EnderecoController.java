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

        if (enderecoSalvo == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(enderecoSalvo);
    }

    @GetMapping("/busca/cep/{cep}")
    public ResponseEntity<List<Endereco>> obterEnderecoPorCEP(@PathVariable String cep) {
        List<Endereco> enderecosPorCep = enderecoService.obterEnderecoPorCEP(cep);

        if (enderecosPorCep == null || enderecosPorCep.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(enderecosPorCep);
    }

    @GetMapping("/busca/id/{id}")
    public ResponseEntity<Endereco> obterEnderecoPorID(@PathVariable Integer id) {
        Endereco endereco = enderecoService.obterEnderecoPorID(id);

        if (endereco == null)
            return  ResponseEntity.notFound().build();

        return ResponseEntity.ok(endereco);
    }

    @GetMapping("/externo/{cep}")
    public ResponseEntity<Endereco> obterEnderecoExterno(@PathVariable String cep) {
        Endereco endereco = enderecoService.obterEnderecoExterno(cep);

        if (endereco == null)
            return  ResponseEntity.notFound().build();

        return ResponseEntity.ok(endereco);
    }

    @GetMapping("/cidade/id/{id}")
    public ResponseEntity<Cidade> obterCidadePorID(@PathVariable Integer id) {
        Cidade cidade = enderecoService.obterCidadePorId(id);

        if (cidade == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(cidade);
    }

    @GetMapping("/cidade/nome/{nome}")
    public ResponseEntity<List<Cidade>> obterCidadePorNome(@PathVariable String nome) {
        List<Cidade> cidades = enderecoService.obterCidadePorNome(nome);

        if (cidades == null || cidades.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(cidades);
    }
}
