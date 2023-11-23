package Componentes.CNF;

import java.util.*;

import Componentes.Gramatica;

public class FormaNormalChomsky {
  public static Map<String, List<String>> convertToCNF(Map<String, List<String>> glc) {
    Map<String, List<String>> glcCopy = Gramatica.clonarGramatica(glc);
    ConverterNaoTerminais.converterNaoTerminais(glcCopy);

    // for (Map.Entry<String, List<String>> each : glc.entrySet()) {
    //   String naoTerminal = each.getKey();
    //   List<String> regras = each.getValue();

    //   List<String> regras_copy = new ArrayList<>(regras);

    //   for (String each_regra : regras_copy) {
    //     String verificarChomsky = new String(each_regra);
    //     while (!Gramatica.isValidChomsky(verificarChomsky)) {
    //       // verificarChomsky = tratarCasoInvalido(glcCopy, verificarChomsky, regras_copy, naoTerminal);
    //     }
    //   }
    // }
    return glcCopy;
  }

  /**
   * S -> {JAAA | JAA | JA | a}
   * A -> {JAA | JA | a}
   * G -> {a}
   */
}
