package Componentes.CNF;

import java.util.*;

import Componentes.*;

/**
 * Classe para o último passo da Forma Normal de Chomsky. Converter os não
 * terminais remanecentes
 * gerando apenas duplas de não terminais ou um terminal.
 */
public class ConverterNaoTerminais {
    /**
     * Método para fazer a conversão em si. Exemplo de entrada e saída:
     * Entrada:
     * S -> YA | a
     * A -> YAA | a
     * 
     * Saída:
     * S -> YA | a
     * A -> Y1A | a
     * Y1 -> YA
     * 
     * @param glc
     */
    public static Map<String, List<String>> converterNaoTerminais(Map<String, List<String>> glc) {
        Map<String, String> substituicoes = new HashMap<>();
        List<String> naoTerminais = Gramatica.pegarNaoTerminais(glc);

        for (Map.Entry<String, List<String>> entry : glc.entrySet()) {
            List<String> regras = entry.getValue();

            for (int i = 0; i < regras.size(); i++) {
                String regra = regras.get(i);
                if (regra.length() > 2) {
                    String substituido = substituirDuplasMaiusculas(regra, substituicoes, naoTerminais);
                    regras.set(i, substituido);
                }
            }
        }

        // Adicionar as substituições ao glc
        for (Map.Entry<String, String> each : substituicoes.entrySet()) {
            glc.put(each.getKey(), List.of(each.getValue()));
        }

        return glc;
    }

    /**
     * Método para fazer a substituição de duplas maiusculas para criar um novo não
     * terminal.
     * 
     * @param original      - string original recebida por parametro.
     * @param substituicoes - lista com substituições que já foram feitas.
     * @param naoTerminais  - lista de não terminais presentes na gramática.
     * @return
     */
    public static String substituirDuplasMaiusculas(String original, Map<String, String> substituicoes,
            List<String> naoTerminais) {

        String resultado = original;
        List<String> splitResultado = Gramatica.dividirString(resultado);

        // Enquanto o vetor 'splitResultado' não tiver tamanho igual a dois, há
        // operações para fazer na string.
        for (int i = splitResultado.size() - 1; splitResultado.size() != 2; i--) {
            String letra = splitResultado.get(i - 1) + splitResultado.get(i);
            String keyExistente = verificarLista(letra, substituicoes);
            if (keyExistente != "") {
                resultado = resultado.replace(letra, keyExistente);
            } else {
                String substituicao = substituirDupla(letra, naoTerminais);
                if (!naoTerminais.contains(substituicao)) {
                    naoTerminais.add(substituicao);
                    substituicoes.put(substituicao, letra);
                    resultado = resultado.replace(letra, substituicao);
                }
            }

            splitResultado = Gramatica.dividirString(resultado);
            i = splitResultado.size();
        }

        return resultado;
    }

    public static String verificarLista(String letra, Map<String, String> substituicoes) {
        for (Map.Entry<String, String> each : substituicoes.entrySet()) {
            String key = each.getKey();
            String value = each.getValue();

            if (value.equals(letra)) {
                return key;
            }
        }

        return "";
    }

    /**
     * Método para substitur uma string pela primeira letra + número do parametro.
     * 
     * @param dupla    - string para pegar a primeira letra.
     * @param contador - contador para concatenar com a string
     * @return - uma string no formato uma letra maiuscula e um número.
     */
    public static String substituirDupla(String dupla, List<String> naoTerminais) {
        String letraRandom = ConverterTerminais.gerarLetraMaiusculaAleatoria(naoTerminais);
        return letraRandom;
    }
}