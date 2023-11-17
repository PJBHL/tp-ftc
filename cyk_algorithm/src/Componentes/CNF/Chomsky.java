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

  // private int qtdLetraMaiusculaCalc(String palavra){

  // }

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
  private Map<String, List<String>> removendoLambdadaGramatica(List<String> transicoesVazias,
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
          System.out.println(elementoLista);
          if (elementoLista.contains(eachTV)) {
            System.out.println(contemLetrasMaiusculasIguais(elementoLista));

            if (contemLetrasMaiusculasIguais(elementoLista)) {
              "".toString();

              List<String> possibilidades = new ArrayList<>();
              // BaBaB

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

              "".toString();

              String resultado = elementoLista.replace(eachTV, "");

              if (resultado != "" && !regrasCopy.contains(resultado)) {
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