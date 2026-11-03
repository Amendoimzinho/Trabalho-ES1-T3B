package com.trabalho_es_3b.trabalhot3b.dao;

import com.trabalho_es_3b.trabalhot3b.model.*;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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

    public boolean cadastrarCidade(Cidade cidade){
        List<Cidade> cidadesComMesmoNome = obterCidadesPorNome(cidade.getNome());

        if (cidadesComMesmoNome.isEmpty()){
            String sql = "INSERT INTO \"Cidade\" (\"nomeCidade\", \"idEstado\")\n" +
                    "VALUES ('teste1', 5);";
        }
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


    public Endereco cadastrarEndereco(Endereco endereco){
        Cidade cidade = endereco.getCidade();
        String nomeCidade = cidade.getNome();

        UnidadeFederacao estado = cidade.getUnidadeFederacao();
        String nomeEstado = estado.getNome();
        String SiglaEstado = estado.getSigla();

        String nomeBairro = endereco.getBairro().getNome();

        Logradouro logradouro = endereco.getLogradouro();
        String nomeLogradouro = logradouro.getNome();
        String tipoNomeLogradouro = logradouro.getTipoLogradouro().getNome();

        String cep = endereco.getCEP();
    }
}
