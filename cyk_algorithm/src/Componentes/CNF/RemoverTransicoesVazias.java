package Componentes.CNF;

import Componentes.Gramatica;

import java.util.*;

public class RemoverTransicoesVazias {

  static Map<String, List<String>> glc;

  public RemoverTransicoesVazias(Gramatica gramatica) {
    glc = gramatica.getGramaticaLida();
  }

  /**
   * 
   * @param transicoesVazias
   * @param glcCopy
   * @return
   */
  private static List<String> pegarTransicoesVazias(List<String> transicoesVazias, Map<String, List<String>> copyGlc) {

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
   * 
   * @param transicoesVazias
   * @param glc
   * @return
   */
  private static Map<String, List<String>> removendoLambdadaGramatica(List<String> transicoesVazias,
      Map<String, List<String>> glc) {

    Map<String, List<String>> glcCopy = new LinkedHashMap<>(glc);

    for (Map.Entry<String, List<String>> each : glc.entrySet()) {
      String naoTerminal = each.getKey();
      List<String> regras = each.getValue();
      if (regras.contains("!")) {
        regras.remove("!");
      }
      List<String> regrasCopy = new ArrayList<>(regras);

      for (String elementoLista : regras) {
        for (String eachTV : transicoesVazias) {
          if (elementoLista.contains(eachTV)) {

            if (elementoLista.length() == 2) {

              String resultado = elementoLista.replace(eachTV, "");

              if (!regrasCopy.contains(resultado)) {
                regrasCopy.add(resultado);

              }

            } else {

              List<String> possibilidades = teste.iniciandoDerivacaoPalavra(elementoLista, eachTV.charAt(0));

              for (String string : possibilidades) {
                regrasCopy.add(string);
              }
            }

            "".toString();
          }
        }

      }
      glcCopy.put(naoTerminal, regrasCopy);

    }

    return glcCopy;

  }

  public static Map<String, List<String>> eliminarProducoesVazias(Map<String, List<String>> copiaMap) {
    List<String> transicoesVazias = new ArrayList<>();
    transicoesVazias.add("!");

    Map<String, List<String>> copiaMapTransicoes = Gramatica.clonarGramatica(copiaMap);

    // Pega lista de transicoes
    transicoesVazias = pegarTransicoesVazias(transicoesVazias, copiaMap);

    // Inicio da remocao de lambda
    copiaMapTransicoes = removendoLambdadaGramatica(transicoesVazias, copiaMapTransicoes);
    "".toString();

    return copiaMapTransicoes;

  }
}