package Componentes.CNF;

import Componentes.Gramatica;
import java.util.*;

/**
 * Primeiro passo do forma de chomsky:
 * Remover as transições vazias e adicionar as regras necessárias.
 */
public class RemoverTransicoesVazias {
  /**
   * Método para pegar os Não Terminais com as transições vazias, diretos e
   * indiretos.
   * 
   * @param transicoesVazias - lista com os não terminais que contém lambda.
   * @param copyGlc          - cópia da gramática.
   * @return - retorna a lista de transições vazias.
   */
  public static List<String> pegarTransicoesVazias(List<String> transicoesVazias, Map<String, List<String>> copyGlc) {
    for (Map.Entry<String, List<String>> each : copyGlc.entrySet()) {
      String naoTerminal = each.getKey();
      List<String> regrasCopy = each.getValue();

      for (String eachTV : transicoesVazias) {
        if (regrasCopy.contains(eachTV) && eachTV != naoTerminal) {
          regrasCopy.remove(eachTV);
          transicoesVazias.add(naoTerminal);

          return pegarTransicoesVazias(transicoesVazias, copyGlc);
        }
      }
    }

    transicoesVazias.remove("!");
    return transicoesVazias;
  }

  /**
   * Método para remover o lambda da gramática, concertando e criando novas regras
   * caso necessário.
   * 
   * @param transicoesVazias - lista com os não terminais que possuem transições
   *                         vazias (diretas e indiretas).
   * @param glc              - Gramática usada.
   * @return - Retorna uma gramática com o lambda removido.
   */
  private static Map<String, List<String>> removendoLambdadaGramatica(List<String> transicoesVazias,
      Map<String, List<String>> glc) {

    Map<String, List<String>> glcCopy = new LinkedHashMap<>(glc);

    for (Map.Entry<String, List<String>> each : glc.entrySet()) {
      String naoTerminal = each.getKey();
      List<String> regras = each.getValue();
      if (regras.contains("!"))
        regras.remove("!");

      List<String> regrasCopy = new ArrayList<>(regras);

      for (String elementoLista : regras) {
        for (String eachTV : transicoesVazias) {
          if (elementoLista.contains(eachTV)) {
            if (elementoLista.length() == 2) {
              String resultado = elementoLista.replace(eachTV, "");

              if (!regrasCopy.contains(resultado))
                regrasCopy.add(resultado);

            } else {
              List<String> possibilidades = Derivacoes.derivacaoPalavra(elementoLista, eachTV.charAt(0));

              for (String string : possibilidades) {
                if (string != "")
                  regrasCopy.add(string);
              }
            }
          }
        }

      }
      glcCopy.put(naoTerminal, regrasCopy);
    }
    return glcCopy;
  }

  /**
   * Método para chamar as outras funções.
   * 
   * @param copiaMap - Cópia da gramática lida para manipulação.
   * @return
   */
  public static Map<String, List<String>> eliminarProducoesVazias(Map<String, List<String>> copiaMap) {
    List<String> transicoesVazias = new ArrayList<>();
    transicoesVazias.add("!");

    Map<String, List<String>> copiaMapTransicoes = Gramatica.clonarGramatica(copiaMap);

    // Pega lista de transicoes
    transicoesVazias = pegarTransicoesVazias(transicoesVazias, copiaMap);

    // Inicio da remocao de lambda
    copiaMapTransicoes = removendoLambdadaGramatica(transicoesVazias, copiaMapTransicoes);

    // Add lambda no estado inicial
    if (transicoesVazias.contains("S")) {

      List<String> regras_cpy = new ArrayList<>(copiaMapTransicoes.get("S"));
      regras_cpy.add("!");
      copiaMapTransicoes.put("S", regras_cpy);

    }
    return copiaMapTransicoes;
  }
}