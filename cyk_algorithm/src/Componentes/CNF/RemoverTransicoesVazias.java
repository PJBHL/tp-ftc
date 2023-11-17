package Componentes.CNF;

import Componentes.Gramatica;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
   * @param input
   * @return
   */
  private static boolean contemLetrasMaiusculasIguais(String input) {
    Set<Character> letrasMaiusculas = new HashSet<>();

    for (char caractere : input.toCharArray()) {
      if (Character.isUpperCase(caractere)) {
        // Se já contém a letra maiúscula, retornar true
        if (letrasMaiusculas.contains(caractere)) {
          return true;
        }
        letrasMaiusculas.add(caractere);
      }
    }

    // Se chegou até aqui, significa que não há letras maiúsculas iguais
    return false;
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
            System.out.println(contemLetrasMaiusculasIguais(elementoLista));

            if (contemLetrasMaiusculasIguais(elementoLista)) {
              "".toString();

              System.out.println("Elemento lista: " + elementoLista + " EACHTV: " + eachTV + "\n\n\n");
              List<String> possibilidades = teste.iniciandoDerivacaoPalavra(elementoLista, eachTV.charAt(0));
              System.out.println("POSSIBILIDADES: " + possibilidades);

              "".toString();

              String resultado = elementoLista.replace(eachTV, "");

              if (resultado != "" && !regrasCopy.contains(resultado)) {
                for (String string : possibilidades) {
                  regrasCopy.add(string);
                }
                regrasCopy.add(resultado);
              }
            } else {

              "".toString();

              String resultado = elementoLista.replace(eachTV, "");

              if (resultado != "" && !regrasCopy.contains(resultado)) {
                regrasCopy.add(resultado);
              }
            }

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

    transicoesVazias = pegarTransicoesVazias(transicoesVazias, copiaMap);
    copiaMap = removendoLambdadaGramatica(transicoesVazias, copiaMap);
    "".toString();

    return copiaMap;

  }
}