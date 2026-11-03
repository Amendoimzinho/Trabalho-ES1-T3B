package com.trabalho_es_3b.trabalhot3b.util;

import java.util.Map;

public final class Utils {

    // Mapa de Estados para converter a UF do ViaCEP
    private static final Map<String, String> ESTADOS_BRASIL = Map.ofEntries(
            Map.entry("AC", "ACRE"), Map.entry("AL", "ALAGOAS"), Map.entry("AP", "AMAPÁ"),
            Map.entry("AM", "AMAZONAS"), Map.entry("BA", "BAHIA"), Map.entry("CE", "CEARÁ"),
            Map.entry("DF", "DISTRITO FEDERAL"), Map.entry("ES", "ESPÍRITO SANTO"),
            Map.entry("GO", "GOIÁS"), Map.entry("MA", "MARANHÃO"), Map.entry("MT", "MATO GROSSO"),
            Map.entry("MS", "MATO GROSSO DO SUL"), Map.entry("MG", "MINAS GERAIS"),
            Map.entry("PA", "PARÁ"), Map.entry("PB", "PARAÍBA"), Map.entry("PR", "PARANÁ"),
            Map.entry("PE", "PERNAMBUCO"), Map.entry("PI", "PIAUÍ"), Map.entry("RJ", "RIO DE JANEIRO"),
            Map.entry("RN", "RIO GRANDE DO NORTE"), Map.entry("RS", "RIO GRANDE DO SUL"),
            Map.entry("RO", "RONDÔNIA"), Map.entry("RR", "RORAIMA"), Map.entry("SC", "SANTA CATARINA"),
            Map.entry("SP", "SÃO PAULO"), Map.entry("SE", "SERGIPE"), Map.entry("TO", "TOCANTINS")
    );

    private Utils() {
        throw new UnsupportedOperationException("Classe utilitária - não deve ser instanciada.");
    }

    public static boolean isCepValido(String cep) {
        if (cep == null) {
            return false;
        }
        return cep.trim().matches("^(\\d{8}|\\d{5}-\\d{3})$");
    }

    public static String formatarCep(String cep) {
        if (cep == null) return null;
        return cep.trim().replaceAll("\\D", "");
    }

    public static String formatarNome(String nome) {
        if (nome == null) return null;
        return nome.trim().toUpperCase();
    }

    // Usado pelo obterEnderecoExterno para tratar o retorno do ViaCEP
    public static String[] separarLogradouro(String logradouroCompleto) {
        String logradouroFormatado = formatarNome(logradouroCompleto);

        if (logradouroFormatado == null || logradouroFormatado.isEmpty()) {
            return new String[]{"OUTROS", "NAO INFORMADO"};
        }

        // Separa a string em no máximo 2 partes (o tipo e o nome)
        String[] partes = logradouroFormatado.split(" ", 2);

        if (partes.length == 2) {
            return new String[]{partes[0], partes[1]};
        }

        return new String[]{"RUA", logradouroFormatado};
    }

    // Usado pelo obterEnderecoExterno para pegar o nome completo a partir da UF do ViaCEP
    public static String obterNomeEstado(String uf) {
        if (uf == null || uf.trim().isEmpty()) return "NÃO INFORMADO";
        return ESTADOS_BRASIL.getOrDefault(uf.trim().toUpperCase(), "NÃO INFORMADO");
    }
}