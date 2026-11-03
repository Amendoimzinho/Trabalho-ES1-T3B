package com.trabalho_es_3b.trabalhot3b.dao;

import com.trabalho_es_3b.trabalhot3b.model.*;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
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
                int idEndereco = rs.getInt("idEndereco");
                int idCidade = rs.getInt("idCidade");
                int idBairro  = rs.getInt("idBairro");
                int idLogradouro  = rs.getInt("idRua");

                Cidade cidade = obterCidadePorId(idCidade);
                Bairro bairro = obterBairroPorId(idBairro);
                Logradouro logradouro = obterRuaPorId(idLogradouro);

                enderecos.add(new Endereco(cidade, bairro, logradouro, cep, idEndereco));
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

                endereco = new Endereco(cidade, bairro, logradouro, cep, id);
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
        String sql =   "SELECT * FROM Bairro WHERE idBairro = " + id.toString();

        return jdbc.query(sql, rs -> {
            Bairro bairro = null;

            while(rs.next()) {
                String nomeBairro = rs.getString("nomeBairro");

                bairro = new Bairro(nomeBairro);
            }

            return bairro;
        });
    }

    public Logradouro obterRuaPorId(Integer id){
        String sql =   "SELECT * FROM Rua WHERE idRua = " + id.toString();

        return jdbc.query(sql, rs -> {
            Logradouro logradouro = null;

            while(rs.next()) {
                String nomeRua = rs.getString("nomeRua");
                int idTipoLogradouro = rs.getInt("idTipoLogradouro");

                TipoLogradouro tipoLogradouro = obterTipoLogradouroPorId(idTipoLogradouro);

                logradouro = new Logradouro(tipoLogradouro, nomeRua);
            }

            return logradouro;
        });
    }

    public TipoLogradouro obterTipoLogradouroPorId(Integer id){
        String sql = "SELECT * FROM TipoLogradouro WHERE idTipoLogradouro = " + id.toString();

        return jdbc.query(sql, rs -> {
           TipoLogradouro tipoLogradouro = null;

           while(rs.next()) {
               String nomeTipoLogradouro = rs.getString("nomeTipoLogradouro");

               tipoLogradouro = new TipoLogradouro(nomeTipoLogradouro);
           }

           return tipoLogradouro;
        });
    }

    public List<Cidade> obterCidadesPorNome(String nome){
        String sql = "SELECT * FROM Cidade WHERE nomeCidade = " + nome;

        return jdbc.query(sql, rs -> {
            List<Cidade> cidades = new ArrayList<>();

            while(rs.next()) {
                String nomeCidade = rs.getString("nomeCidade");
                int idEstado = rs.getInt("idEstado");

                UnidadeFederacao estado = obterEstadoPorId(idEstado);

                cidades.add(new Cidade(nomeCidade, estado));
            }

            return cidades;
        });
    }

    public UnidadeFederacao obterEstadoPorId(Integer id){
        String sql = "SELECT * FROM Estado WHERE idEstado = " + id.toString();

        return jdbc.query(sql, rs -> {
            UnidadeFederacao estado = null;

            while(rs.next()) {
                String nomeEstado = rs.getString("nomeEstado");
                String siglaUF =  rs.getString("siglaUF");

                estado = new UnidadeFederacao(nomeEstado, siglaUF);
            }

            return estado;
        });
    }

    // retorna id do cadastro. zero se nao conseguiu.
    public int cadastrarEstado(UnidadeFederacao estado){
        String sql = """
            SELECT * FROM "Estado" WHERE "nomeEstado" = ?
            """;

        boolean estadoExiste = false;
        int indiceEstado = 0;

        jdbc.query(sql, rs -> {
                UnidadeFederacao e = null;

                if (rs.next()) {
                    estadoExiste = true;


                }

                return estado;

            }, estado.getNome()
        );

        boolean estadoExiste = Boolean.TRUE.equals();

        if (!estadoExiste) {
            String sql = """
                INSERT INTO "Cidade" ("nomeCidade, "idEstado")    
                """
        } else {

        }
    }

//    public boolean cadastrarCidade(Cidade cidade){
//        String sql = """
//            SELECT c.*
//            FROM "Cidade" c
//            JOIN "Estado" e ON e."idEstado" = c."idEstado"
//            WHERE c."nomeCidade" = ?
//            AND e."nomeEstado" = ?
//            """;
//
//        boolean existeCidadeIgual = Boolean.TRUE.equals(jdbc.query(
//                sql, ResultSet::next,
//                cidade.getNome(), cidade.getUnidadeFederacao().getNome()
//        ));
//
//        if (!existeCidadeIgual){
//            String
//
//            String sql = """
//                INSERT INTO "Cidade" ("nomeCidade, "idEstado")
//                """
//        }
//    }

    public Endereco cadastrarEndereco(Endereco endereco){

    }
}
