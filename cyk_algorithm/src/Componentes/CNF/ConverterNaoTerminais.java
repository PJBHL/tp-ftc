package Componentes.CNF;

import java.util.*;

import Componentes.Gramatica;

public class ConverterNaoTerminais {

    /**
     * Método para verificar a quantidade de letras maiusculas em determinada regra.
     * 
     * @param texto - regra analisada
     * @return - true se é diferente de 2 letras maiusculas.
     */
    private static boolean verificarQuantidadeMaiusculas(String texto) {
        int contadorMaiusculas = 0;
        for (int i = 0; i < texto.length(); i++) {
            char caractere = texto.charAt(i);
            if (Character.isUpperCase(caractere)) {
                contadorMaiusculas++;
            } else {
                return false;
            }
        }
        return contadorMaiusculas != 2;
    }

    public static void converterNaoTerminais(Map<String, List<String>> mapa) {
        Map<String, String> substituicoes = new HashMap<>();

        for (Map.Entry<String, List<String>> entry : mapa.entrySet()) {
            String key = entry.getKey();
            List<String> values = entry.getValue();

            for (int i = 0; i < values.size(); i++) {
                String original = values.get(i);
                if (original.length() > 2) {
                    String substituido = substituirDuplasMaiusculas(original, substituicoes);
                    values.set(i, substituido);
                }
            }
        }

        // Adicionar as substituições ao mapa
        for (Map.Entry<String, String> entry : substituicoes.entrySet()) {
            mapa.put(entry.getKey(), List.of(entry.getValue()));
        }
    }

    public static String substituirDuplasMaiusculas(String original, Map<String, String> substituicoes) {
        String resultado = original;

        for (int i = 0; i < original.length() - 1; i++) {
            if (Character.isUpperCase(original.charAt(i)) && Character.isUpperCase(original.charAt(i + 1))) {
                String substring = original.substring(i, i + 2);
                String substituicao = substituicoes.get(substring);
                if (substituicao == null) {
                    substituicao = substituirDupla(substring);
                    substituicoes.put(substituicao, substring);
                }
                resultado = resultado.replace(substring, substituicao);
                i = original.length();
            }
        }

        return resultado;
    }

    public static String substituirDupla(String dupla) {
        return dupla.substring(0, 1) + "1";
    }
}
