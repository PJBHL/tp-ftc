package Componentes.CNF;

import java.util.*;

import Componentes.Gramatica;

/**
 * Classe para o segundo passo da gramática de chomsky:
 * Remover Transições Unitárias. Exemplo:
 * 
 * Suponha que uma gramática recebida do primeiro passo (Remoção de Transições
 * Vazias), esteja da seguinte forma:
 * 
 * S -> AB | B | SCB | SB
 * A -> aA | a | C
 * B -> bB | b
 * C -> cC | c
 * 
 * As transições unitárias (S -> B, A -> C, etc) devem ser transformadas.
 * Fecho de variáveis unitárias:
 * S = {B}
 * A = {C}
 * B = {}
 * C = {}
 * 
 * Resultando em:
 * 
 * S -> AB | bB | b | SCB | SB
 * A -> aA | a | cC | c
 * B -> bB | b
 * C -> cC | c
 */
public class RemoverTransicoesUnitarias {
    public static Map<String, List<String>> removerUnitarios(Map<String, List<String>> glc) {
        List<String> transicoesUnitarias = pegarTransicoesUnitarias(glc);
        Map<String, List<String>> glcCopy = removerRegrasInuteis(glc);
        glcCopy = removendoTransicoesUnitarias(transicoesUnitarias, glcCopy);

        return glcCopy;
    }

    /**
     * Remoção possíveis regras inúteis da gramática, geradas na derivação. Exemplo:
     * S -> A | S
     * (S gerando ele mesmo).
     * 
     * @param glc - gramática para remover as regras.
     * @return - nova gramática com regras inúteis removidas.
     */
    public static Map<String, List<String>> removerRegrasInuteis(Map<String, List<String>> glc) {
        Map<String, List<String>> glcCopy = Gramatica.clonarGramatica(glc);

        for (Map.Entry<String, List<String>> each : glc.entrySet()) {
            String naoTerminal = each.getKey();
            List<String> regras = each.getValue();

            if (regras.contains(naoTerminal))
                glcCopy.get(naoTerminal).remove(naoTerminal);
        }

        return glcCopy;
    }

    /**
     * Método para percorrer a gramática e pegar quais não terminais possuem
     * transições vazias.
     * 
     * @param gramatica - gramática.
     * @return - lista de string com variáveis que possuem transição vazia.
     */
    public static List<String> pegarTransicoesUnitarias(Map<String, List<String>> gramatica) {
        List<String> transicoesUnitarias = new ArrayList<>();

        for (Map.Entry<String, List<String>> each : gramatica.entrySet()) {
            String naoTerminal = each.getKey();
            List<String> regras = each.getValue();

            for (String regra : regras) {
                if (regra.length() == 1 && Character.isUpperCase(regra.charAt(0))) {
                    transicoesUnitarias.add(regra);
                }
            }
        }

        return transicoesUnitarias;
    }

    /**
     * Método para pegar as regras de um não terminal que possui transição unitária
     * em algum lugar da gramática.
     * Exemplo:
     * S -> A
     * A -> aA | A
     * return será as regras de A, ou seja, [aA, A].
     * 
     * @param naoTerminal - não terminal para recolher as regras.
     * @param gramatica   - gramática.
     */
    public static List<String> pegarRegras(String naoTerminal, Map<String, List<String>> gramatica) {
        List<String> regrasCopy = new ArrayList<>();

        regrasCopy = gramatica.get(naoTerminal);

        return regrasCopy;
    }

    /**
     * Método para efetivamente remover as transições unitárias gramática.
     * 
     * @param transicoesUnitarias - Lista com não terminais que possuem transição
     *                            unitária.
     * @param gramatica           - gramática.
     * @return - nova gramática sem transições unitárias.
     */
    public static Map<String, List<String>> removendoTransicoesUnitarias(List<String> transicoesUnitarias,
            Map<String, List<String>> gramatica) {
        Map<String, List<String>> novaGramatica = Gramatica.clonarGramatica(gramatica);

        for (Map.Entry<String, List<String>> each : gramatica.entrySet()) {
            String naoTerminal = each.getKey();
            List<String> regras = each.getValue();

            for (String regra : regras) {
                if (transicoesUnitarias.contains(regra)) {
                    List<String> copyRegras = pegarRegras(regra, gramatica);
                    novaGramatica.get(naoTerminal).remove(regra);
                    novaGramatica.get(naoTerminal).addAll(copyRegras);
                    "".toString();
                }
            }
        }

        return novaGramatica;
    }
}