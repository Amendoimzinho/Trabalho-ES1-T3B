package com.trabalho_es_3b.trabalhot3b.util;

public final class Utils {

    // Construtor privado impede a instanciação da classe utilitária
    private Utils() {
        throw new UnsupportedOperationException("Classe utilitária - não deve ser instanciada.");
    }


    public static boolean isCepValido(String cep) {
        if (cep == null) {
            return false;
        }

        // Expressão regular:
        // ^\d{8}$        -> Exatamente 8 dígitos sem separador (ex: 85860000)
        // |              -> OU
        // ^\d{5}-\d{3}$  -> 5 dígitos + hífen + 3 dígitos (ex: 85860-000)
        return cep.trim().matches("^(\\d{8}|\\d{5}-\\d{3})$");
    }

    public static String formatarCep(String cep) {
        if (cep == null)
            return null;

        return cep.trim().replaceAll("\\D", "");
    }

    public static String formatarNome(String nome) {
        if (nome == null)
            return null;

        return nome.trim().toUpperCase();
    }
}