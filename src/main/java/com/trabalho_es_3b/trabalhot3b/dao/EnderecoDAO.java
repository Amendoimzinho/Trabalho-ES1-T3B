package com.trabalho_es_3b.trabalhot3b.dao;

import com.trabalho_es_3b.trabalhot3b.model.*;

import org.springframework.dao.EmptyResultDataAccessException;
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
        String sql = """
        SELECT *
        FROM "Endereco"
        WHERE "cep" = ?
        """;

        return jdbc.query(sql, rs -> {
            List<Endereco> enderecos = new ArrayList<>();
            while (rs.next()) {
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
        }, cep);
    }

    public Endereco obterEnderecoPorId(Integer id){
        String sql = """
        SELECT * 
        FROM "Endereco"
        WHERE "idEndereco" = ?
        """;

        return jdbc.query(sql, rs -> {
            Endereco endereco = null;
            if (rs.next()) {
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
        }, id);
    }

    public Cidade obterCidadePorId(Integer id){
        String sql = """
        SELECT *
        FROM "Cidade"
        WHERE "idCidade" = ?
        """;

        return jdbc.query(sql, rs -> {
             Cidade cidade = null;
             if (rs.next()) {
                 String nomeCidade = rs.getString("nomeCidade");
                 int idEstado = rs.getInt("idEstado");
                 UnidadeFederacao estado = obterEstadoPorId(idEstado);

                 cidade = new Cidade(nomeCidade, estado);
             }
             return cidade;
        }, id);
    }

    public Bairro obterBairroPorId(Integer id){
        String sql = """
        SELECT *
        FROM "Bairro"
        WHERE "idBairro" = ?
        """;

        return jdbc.query(sql, rs -> {
            Bairro bairro = null;
            if (rs.next()) {
                String nomeBairro = rs.getString("nomeBairro");

                bairro = new Bairro(nomeBairro);
            }
            return bairro;
        }, id);
    }

    public Logradouro obterRuaPorId(Integer id){
        String sql = """
        SELECT *
        FROM "Rua"
        WHERE "idRua" = ?
        """;

        return jdbc.query(sql, rs -> {
            Logradouro logradouro = null;
            if (rs.next()) {
                String nomeRua = rs.getString("nomeRua");
                int idTipoLogradouro = rs.getInt("idTipoLogradouro");
                TipoLogradouro tipoLogradouro = obterTipoLogradouroPorId(idTipoLogradouro);

                logradouro = new Logradouro(tipoLogradouro, nomeRua);
            }
            return logradouro;
        }, id);
    }

    public TipoLogradouro obterTipoLogradouroPorId(Integer id){
        String sql = """
        SELECT *
        FROM "TipoLogradouro"
        WHERE idTipoLogradouro = ?
        """;

        return jdbc.query(sql, rs -> {
           TipoLogradouro tipoLogradouro = null;
           if (rs.next()) {
               String nomeTipoLogradouro = rs.getString("nomeTipoLogradouro");

               tipoLogradouro = new TipoLogradouro(nomeTipoLogradouro);
           }
           return tipoLogradouro;
        }, id);
    }

    public List<Cidade> obterCidadesPorNome(String nome){
        String sql = """
        SELECT *
        FROM "Cidade"
        WHERE "nomeCidade" = ?
        """;

        return jdbc.query(sql, rs -> {
            List<Cidade> cidades = new ArrayList<>();
            while (rs.next()) {
                String nomeCidade = rs.getString("nomeCidade");
                int idEstado = rs.getInt("idEstado");
                UnidadeFederacao estado = obterEstadoPorId(idEstado);

                cidades.add(new Cidade(nomeCidade, estado));
            }
            return cidades;
        }, nome);
    }

    public UnidadeFederacao obterEstadoPorId(Integer id){
        String sql = """
        SELECT *
        FROM "Estado"
        WHERE "idEstado = ?
        """;

        return jdbc.query(sql, rs -> {
            UnidadeFederacao estado = null;
            if (rs.next()) {
                String nomeEstado = rs.getString("nomeEstado");
                String siglaUF =  rs.getString("siglaUF");

                estado = new UnidadeFederacao(nomeEstado, siglaUF);
            }
            return estado;
        }, id);
    }

    public Integer cadastrarEstado(UnidadeFederacao estado){
        String sql = """
        SELECT "idEstado"
        FROM "Estado"
        WHERE "nomeEstado" = ?
        """;

        try {
            return jdbc.queryForObject(sql, Integer.class, estado.getNome());
        } catch (EmptyResultDataAccessException e) {

            String insertSql = """
            INSERT INTO "Estado" ("nomeEstado")
            VALUES ('?')
            RETURNING "idEstado"
            """;

            return jdbc.queryForObject(insertSql, Integer.class, estado.getNome());
        }
    }

    public Integer cadastrarCidade(Cidade cidade, Integer idEstado){
        String sql = """
        SELECT c.*
        FROM "Cidade" c
        JOIN "Estado" e ON e."idEstado" = c."idEstado"
        WHERE c."nomeCidade" = ?
        AND e."nomeEstado" = ?
        """;

        try {
            return jdbc.queryForObject(sql, Integer.class,
                    cidade.getNome(), cidade.getUnidadeFederacao().getNome());
        } catch (EmptyResultDataAccessException e) {
            String insertSql = """
            INSERT INTO "Cidade" ("nomeCidade", "idEstado")
            VALUES ('?', ?)
            RETURNING "idCidade"
            """;

            return jdbc.queryForObject(insertSql, Integer.class, cidade.getNome(), idEstado);
        }
    }

    public Integer cadastrarBairro(Bairro bairro){
        String sql = """
        SELECT "idBairro"
        FROM "Bairro"
        WHERE "nomeBairro" = ?
        """;

        try {
            return jdbc.queryForObject(sql, Integer.class, bairro.getNome());
        } catch (EmptyResultDataAccessException e) {
            String insertSql = """
            INSERT INTO "Bairro" ("nomeBairro", "idBairro")
            VALUES ('?', bairro.getNome())
            RETURNING "idBairro"
            """;

            return jdbc.queryForObject(insertSql, Integer.class, bairro.getNome());
        }
    }

    public Integer cadastrarRua(Logradouro logradouro, Integer idTipoLogradouro){
        String sql = """
        SELECT "idRua"
        FROM "Rua"
        WHERE "nomeRua" = ?
        """;

        try {
            return jdbc.queryForObject(sql, Integer.class, logradouro.getNome());
        } catch (EmptyResultDataAccessException e) {
            String insertSql = """
            INSERT INTO "Rua" ("idTipoLogradouro", "nomeRua")
            VALUES (?, '?')
            RETURNING "idRua"
            """;

            return jdbc.queryForObject(insertSql, Integer.class, idTipoLogradouro, logradouro.getNome());
        }
    }

    public Integer cadastrarTipoLogradouro(TipoLogradouro tipoLogradouro){
        String sql = """
        SELECT "idTipoLogradouro"
        FROM "Logradouro"
        WHERE "nomeLogradouro" = ?
        """;

        try {
            return jdbc.queryForObject(sql, Integer.class, tipoLogradouro.getNome());
        } catch (EmptyResultDataAccessException e) {
            String insertSql = """
            INSERT INTO "tipoLogradouro" ("nomeTipoLogradouro")
            VALUES ('?')
            RETURNING "idTipoLogradouro"
            """;

            return jdbc.queryForObject(insertSql, Integer.class, tipoLogradouro.getNome());
        }
    }

    public Endereco cadastrarEndereco(Endereco endereco){
        Integer idEstado = cadastrarEstado(endereco.getCidade().getUnidadeFederacao());
        Integer idCidade = cadastrarCidade(endereco.getCidade(), idEstado);
        Integer idBairro = cadastrarBairro(endereco.getBairro());
        Integer idTipoLogradouro = cadastrarTipoLogradouro(endereco.getLogradouro().getTipoLogradouro());
        Integer idLogradouro = cadastrarRua(endereco.getLogradouro(), idTipoLogradouro);

        String insertSql = """
        INSERT INTO "Endereco" ("idCidade", "idRua", "idBairro", "cep")
        VALUES (?, ?, ?, ?)
        RETURNING "idEndereco"
        """;

        Integer idEndereco = jdbc.queryForObject(insertSql, Integer.class,
                idCidade, idLogradouro, idBairro, endereco.getCEP());

        endereco.setId(idEndereco);

        return endereco;
    }
}
