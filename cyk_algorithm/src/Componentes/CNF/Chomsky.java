package Componentes.CNF;

import Componentes.Gramatica;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Chomsky {

  Map<String, List<String>> glc;

  public Chomsky(Gramatica ut) {
    this.glc = ut.getGramaticaLida();
  }

  /**
   * 
   * @param transicoesVazias
   * @param glcCopy
   * @return
   */
  private List<String> pegarTransicoesVazias(List<String> transicoesVazias, Map<String, List<String>> copyGlc) {

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
  private Map<String, List<String>> removendoLambdadaGramatica(List<String> transicoesVazias,
      Map<String, List<String>> glc) {

    Map<String, List<String>> glcCopy = new HashMap<>(glc);

    for (Map.Entry<String, List<String>> each : glc.entrySet()) {
      String naoTerminal = each.getKey();
      List<String> regras = each.getValue();
      if (regras.contains("!")) {
        regras.remove("!");
      }
      List<String> regrasCopy = new ArrayList<>(regras);

      for (String elementoLista : regras) {
        for (String eachTV : transicoesVazias) {
          System.out.println(elementoLista);
          if (elementoLista.contains(eachTV)) {

            if (elementoLista.length() >= 3) {

              List<String> possibilidades = new ArrayList<>();

              for (int i = 0; i < elementoLista.length(); i++) {
                StringBuilder sb = new StringBuilder(elementoLista);
                if (sb.charAt(i) == eachTV.charAt(0)) {

                  sb.deleteCharAt(i);

                  if (!possibilidades.contains(sb.toString()) && sb.toString() != ""
                      && !regrasCopy.contains(sb.toString())) {
                    possibilidades.add(sb.toString());
                    regrasCopy.add(sb.toString());

                  }

                }
              }
            }

            String resultado = elementoLista.replace(eachTV, "");
            // List<String> combinacoes = gerarCombinacoes(elementoLista);

            if (resultado != "") {
              regrasCopy.add(resultado);
            }

          }
        }

      }
      glcCopy.put(naoTerminal, regrasCopy);

    }

    return glcCopy;

  }

  public void eliminarProducoesVazias() {
    List<String> transicoesVazias = new ArrayList<>();
    transicoesVazias.add("!");

    Map<String, List<String>> copiaMap = new HashMap<>();
    for (Map.Entry<String, List<String>> entry : this.glc.entrySet()) {
      List<String> originalLista = entry.getValue();
      List<String> copiaLista = new ArrayList<>(originalLista);
      copiaMap.put(entry.getKey(), copiaLista);
    }

    transicoesVazias = pegarTransicoesVazias(transicoesVazias, copiaMap);
    copiaMap = removendoLambdadaGramatica(transicoesVazias, this.glc);
    "".toString();

  }
}