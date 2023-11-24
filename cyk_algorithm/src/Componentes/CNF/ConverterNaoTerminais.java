package Componentes.CNF;

import java.util.*;

import Componentes.Gramatica;

public class ConverterNaoTerminais {
    public static void converterNaoTerminais(Map<String, List<String>> mapa) {
        Map<String, String> substituicoes = new HashMap<>();
        List<String> naoTerminais = Gramatica.pegarNaoTerminais(mapa);

        for (Map.Entry<String, List<String>> entry : mapa.entrySet()) {
            String key = entry.getKey();
            List<String> values = entry.getValue();

            for (int i = 0; i < values.size(); i++) {
                String original = values.get(i);
                if (original.length() > 2) {
                    String substituido = substituirDuplasMaiusculas(original, substituicoes, naoTerminais);
                    values.set(i, substituido);
                }
            }
        }

        // Adicionar as substituições ao mapa
        for (Map.Entry<String, String> entry : substituicoes.entrySet()) {
            mapa.put(entry.getKey(), List.of(entry.getValue()));
        }
    }

    /**
     * S -> {JAAAA | JAA | JA | a}
     * A -> {JAA | JA | a}
     * G -> {a}
     * Map<String, String> = J1 -> JA
     * Map<String, String> = A1 -> AA
     * 
     * J1A1A
     * Map<String, String> A2 -> A1A
     * A2A
     * 
     * S -> {M1AA | M1A | MA | a}
     * A -> {M1A | MA | a}
     * U -> {a}
     * M1 -> MA
     * 
     * S -> {J3A | J1A | JA | a}
     * A -> {J1A | JA | a}
     * G -> {a}
     * J1 -> JA
     * J2 -> J1A
     * J3 -> J2A
     * 
     */
    public static List<String> dividirString(String input) {
        List<String> newArray = new ArrayList<>();

        String regra = String.valueOf(input.charAt(0));

        for (int i = 1; i < input.length(); i++) {
            if (Character.isUpperCase(input.charAt(i))) {
                newArray.add(regra);
                regra = String.valueOf(input.charAt(i));
            } else {
                regra += String.valueOf(input.charAt(i));
            }
        }

        newArray.add(regra);

        return newArray;
    }

    public static String substituirDuplasMaiusculas(String original, Map<String, String> substituicoes,
            List<String> naoTerminais) {
        String resultado = original;
        boolean verificar = false;

        List<String> splitResultado = dividirString(resultado);
        int pos = 1;

        for (int i = 0, contador = 1; splitResultado.size() != 2; i++, contador++) {
            String letra = splitResultado.get(i) + splitResultado.get(i + 1);

            // String substring = letra + splitResultado.get(i + 2);
            // substring = letra
            String substituicao = substituicoes.get(letra);
            substituicao = substituirDupla(letra, contador);
            if (!naoTerminais.contains(substituicao)) {
                naoTerminais.add(substituicao);
                substituicoes.put(substituicao, letra);
                resultado = resultado.replace(letra, substituicao);
            } else {
                resultado = resultado.replace(letra, substituicao);
            }
            i--;

            splitResultado = dividirString(resultado);
        }

        return resultado;
    }

    public static String substituirDupla(String dupla, int contador) {
        return dupla.substring(0, 1) + String.valueOf(contador);
    }
}
