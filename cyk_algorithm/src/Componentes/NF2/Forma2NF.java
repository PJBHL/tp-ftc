package Componentes.NF2;

import java.util.*;
import Componentes.Gramatica;
import Componentes.CNF.*;

/**
 * Classe para a conversão de uma gramática para o padrão
 * descrito no artigo, a forma binária 2NF.
 */
public class Forma2NF {

    public static Map<String, List<String>> glc;

    public Forma2NF(Gramatica gramatica) {
        glc = gramatica.getGramaticaLida();

        System.out.println("\nGramatica Lida: \n");
        Gramatica.imprimirGramatica(glc);

        // Removendo possíveis inuteis da gramática.
        System.out.println("\nGramatica tirando os inuteis: \n");
        glc = RemoverTransicoesInuteis.removerInuteis(glc);
        Gramatica.imprimirGramatica(glc);

        System.out.println("\nGramatica em 2NF: \n");
        glc = ConverterNaoTerminais.converterNaoTerminais(glc);
        Gramatica.imprimirGramatica(glc);
    }

    public static Map<String, List<String>> getGlc() {
        return glc;
    }

    /**
     * Método para pegar os não terminais que possuem transições lambdas diretas e
     * indiretas.
     * 
     * @param glc - gramática para coletar os lambdas.
     * @return - lista com os não terminais que geram transições vazias.
     */
    public static List<String> nullable(Map<String, List<String>> glc) {
        List<String> transicoesVazias = new ArrayList<>();
        Map<String, List<String>> glcCopy = Gramatica.clonarGramatica(glc);
        transicoesVazias.add("!");

        transicoesVazias = RemoverTransicoesVazias.pegarTransicoesVazias(transicoesVazias, glcCopy);

        return transicoesVazias;
    }

    /**
     * Método para pegar uma gramática com os elementos unitários.
     * 
     * @param glc       - gramática para retornar uma gramática unitária.
     * @param nullables - lista com os não terminais que geram transições vazias.
     * @return - uma nova gramática com apenas unitários.
     */
    public static Map<String, List<String>> pegarUnitarios(Map<String, List<String>> glc, List<String> nullables) {
        Map<String, List<String>> glcUnitarios = new LinkedHashMap<>();

        for (Map.Entry<String, List<String>> entry : glc.entrySet()) {
            String naoTerminal = entry.getKey();
            List<String> regras = entry.getValue();
            List<String> adicionaveis = new ArrayList<>();

            for (String regra : regras) {
                if (regra.length() >= 2) {
                    // Unitários juntos de não terminais que formam lambda.
                    if (nullables.stream().anyMatch(regra::contains)) {
                        if (regra.matches(".*[a-z].*")) {
                            String terminal = new String(regra).replaceAll("[A-Z]", "");
                            adicionaveis.add(terminal);
                        } else
                            for (String nullable : nullables) {
                                if (regra.contains(nullable)) {
                                    // Verifica se o próximo caractere depois do nullable é um número.
                                    // Se for um número significa que ele na verdade é um outro não terminal gerado
                                    // aleatoriamente.
                                    List<String> splitString = Gramatica.dividirString(regra);
                                    for (String split : splitString) {
                                        if (split.matches(".*\\d.*")) {
                                            adicionaveis.add(split);
                                        }
                                    }
                                }
                            }
                    }
                } else if (!regra.equals("!")) // Se a regra é diferente de lambda, nesse ponto já é unitária.
                    adicionaveis.add(regra);
            }
            if (!adicionaveis.isEmpty() && !adicionaveis.contains(""))
                glcUnitarios.put(naoTerminal, new ArrayList<>(adicionaveis));
        }

        return glcUnitarios;
    }

    /**
     * Método para pegar a transitividade de cada elemento da gramática de
     * unitários,
     * ou seja, a partir de um elemento X chego nos elementos w, y, z ...
     * 
     * @param glc - gramática com unitários.
     * @return - nova gramática com a transitividade de cada não terminal.
     */
    public static Map<String, List<String>> glcTransitividade(Map<String, List<String>> glc) {
        Map<String, List<String>> gramaticaTransitiva = Gramatica.clonarGramatica(glc);

        // Criando gramática de transitividade.
        boolean modificado;
        do {
            modificado = false;
            for (Map.Entry<String, List<String>> entry : gramaticaTransitiva.entrySet()) {
                List<String> value = entry.getValue();
                List<String> novosElementos = new ArrayList<>();

                // Verificando a transitividade
                for (String elemento : value) {
                    if (gramaticaTransitiva.containsKey(elemento)) {
                        List<String> transitividadeElemento = gramaticaTransitiva.get(elemento);
                        for (String transitividade : transitividadeElemento) {
                            if (!value.contains(transitividade) && !novosElementos.contains(transitividade)) {
                                novosElementos.add(transitividade);
                                modificado = true;
                            }
                        }
                    }
                }

                // Adicionando novos elementos à lista
                value.addAll(novosElementos);
            }
        } while (modificado);

        return gramaticaTransitiva;
    }

    /**
     * Método para fazer o inverso da gramática anterior, ou seja, esse elemento X
     * chega-se nele por w, y, z...
     * 
     * @param glc - gramática com a transitividade de cada elemento.
     * @return - gramática com o inverso da transitividade de cada elemento.
     */
    public static Map<String, List<String>> inversoTransitiva(Map<String, List<String>> glc) {
        Map<String, List<String>> invertedMap = new LinkedHashMap<>();

        for (Map.Entry<String, List<String>> entry : glc.entrySet()) {
            String key = entry.getKey();
            List<String> values = entry.getValue();

            if (!invertedMap.containsKey(key)) {
                invertedMap.put(key, new ArrayList<>());
                invertedMap.get(key).add(key); // Adiciona o próprio elemento à lista
            }

            for (String value : values) {
                if (!invertedMap.containsKey(value)) {
                    invertedMap.put(value, new ArrayList<>());
                    invertedMap.get(value).add(value);
                }
                invertedMap.get(value).add(key);
            }
        }

        invertedMap.remove(glc.keySet().iterator().next());

        return invertedMap;
    }
}
