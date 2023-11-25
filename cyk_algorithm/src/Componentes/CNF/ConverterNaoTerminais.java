package Componentes.CNF;

import java.util.*;

import Componentes.Gramatica;

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
     * Método para dividr uma string em posições de um array.
     * A trava da divisão é um número. Exemplo de entrada e saída:
     * Entrada:
     * CAAA
     * Saída:
     * Lista com C A A A
     * Entrada com número:
     * C1AA
     * Saída:
     * Lista com C1 A A
     */
    public static List<String> dividirString(String input) {
        List<String> newArray = new ArrayList<>();

        String regra = String.valueOf(input.charAt(0));

        for (int i = 1; i < input.length(); i++) {
            if (!Character.isDigit(input.charAt(i))) {
                newArray.add(regra);
                regra = String.valueOf(input.charAt(i));
            } else {
                regra += String.valueOf(input.charAt(i));
            }
        }

        newArray.add(regra);

        return newArray;
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
        List<String> splitResultado = dividirString(resultado);

        // Enquanto o vetor 'splitResultado' não tiver tamanho igual a dois, há
        // operações para fazer na string.
        for (int i = splitResultado.size() - 1; splitResultado.size() != 2; i--) {
            String letra = splitResultado.get(i - 1) + splitResultado.get(i);
            String substituicao = substituicoes.get(letra);
            substituicao = substituirDupla(letra, naoTerminais);
            if (!naoTerminais.contains(substituicao)) {
                naoTerminais.add(substituicao);
                substituicoes.put(substituicao, letra);
                resultado = resultado.replace(letra, substituicao);
            } else {
                resultado = resultado.replace(letra, substituicao);
            }

            splitResultado = dividirString(resultado);
        }

        return resultado;
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