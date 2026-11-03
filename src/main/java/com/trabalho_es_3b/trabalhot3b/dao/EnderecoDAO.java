package com.trabalho_es_3b.trabalhot3b.dao;

import com.trabalho_es_3b.trabalhot3b.model.*;

import com.trabalho_es_3b.trabalhot3b.util.DBConn;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EnderecoDAO {
    private final JdbcTemplate jdbc;

    public EnderecoDAO(JdbcTemplate jdbcTemplate) { this.jdbc = jdbcTemplate; }

    public List<Endereco> obterEnderecosPorCEP(String cep) {
        String sql =  "SELECT * FROM Endereco WHERE cep = " + cep;

        return jdbc.query(sql, rs -> {
            List<Endereco> enderecos = new ArrayList<>();

            while(rs.next()) {
                int idCidade = rs.getInt("idCidade");
                int idBairro  = rs.getInt("idBairro");
                int idLogradouro  = rs.getInt("idRua");

                Cidade cidade = obterCidadePorId(idCidade);
                Bairro bairro = obterBairroPorId(idBairro);
                Logradouro logradouro = obterRuaPorId(idLogradouro);

                enderecos.add(new Endereco(cidade, bairro, logradouro, cep));
            }

            return enderecos;
        });
    }

    public Endereco obterEnderecoPorId(Integer id){
        String sql =  "SELECT * FROM Endereco WHERE idEndereco = " + id.toString();

        return jdbc.query(sql, rs -> {
            Endereco endereco = null;

            while(rs.next()) {
                int idCidade = rs.getInt("idCidade");
                int idBairro  = rs.getInt("idBairro");
                int idLogradouro  = rs.getInt("idRua");
                String cep = rs.getString("cep");

                Cidade cidade = obterCidadePorId(idCidade);
                Bairro bairro = obterBairroPorId(idBairro);
                Logradouro logradouro = obterRuaPorId(idLogradouro);

                endereco = new Endereco(cidade, bairro, logradouro, cep);
            }

            return endereco;
        });
    }

    public Cidade obterCidadePorId(Integer id){
        String sql =   "SELECT * FROM Cidade WHERE idCidade = " + id.toString();

        return jdbc.query(sql, rs -> {
             Cidade cidade = null;

             while(rs.next()) {
                 String nomeCidade = rs.getString("nomeCidade");
                 int idEstado = rs.getInt("idEstado");

                 UnidadeFederacao estado = obterEstadoPorId(idEstado);

                 cidade = new Cidade(nomeCidade, estado);
             }

             return cidade;
        });
    }

    public Bairro obterBairroPorId(Integer id){
        return null;
    }

    public Logradouro obterRuaPorId(Integer id){
        return null;
    }

    public Cidade obterCidadePorNome(String nome){
        return null;
    }

    public UnidadeFederacao obterEstadoPorId(Integer id){
        return null;
    }

    public Endereco salvarEndereco(Endereco endereco){
        return null;
    }
}
